package com.gb.yun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.gb.common.constant.CommonConstant;
import com.gb.utils.JsonUtil;
import com.gb.utils.RedisUtils;
import com.gb.utils.exception.BusinessException;
import com.gb.utils.exception.ParameterNullException;
import com.gb.yun.enmus.YunFunctionEnum;
import com.gb.yun.enmus.YunObjectCodeEnum;
import com.gb.yun.enmus.YunSyncDataTypeEnum;
import com.gb.yun.entity.bo.YunDicDataInsertBO;
import com.gb.yun.entity.bo.YunDicDataQueryBO;
import com.gb.yun.entity.vo.ApiRequestVO;
import com.gb.yun.service.YunCommonService;
import com.gb.yun.service.YunDicDataService;
import com.gb.yun.service.YunService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.gb.yun.enmus.YunFunctionEnum.OBJECT_QUERY;

;

/**
 * <p>
 * 工保云服务接口实现类
 * </p>
 *
 * @author sunx
 * @since 2021-03-17
 */
@Slf4j
@Service
@Setter(onMethod_ = {@Autowired})
public class YunCommServiceImpl implements YunCommonService {

    private StringRedisTemplate stringRedisTemplate;

    private YunService yunService;

    private YunDicDataService yunDicDataService;

    @Override
    public Object functionSend(YunFunctionEnum funcEnum, Object reqObj) throws Exception {
        Map<String, Object> resultMap  = validateProcess(new Function<String, String>() {
            @SneakyThrows
            @Override
            public String apply(String token) {
                switch (funcEnum){
                    case XZQH_TREE:
                        return yunService.treeData(token);
                    case OBJECT_QUERY:
                        ApiRequestVO vo = (ApiRequestVO)reqObj;
                        vo.setToken(token);
                        return yunService.findAll(vo);
                    case DICDATA_QUERY:
                        YunDicDataQueryBO queryBo = (YunDicDataQueryBO)reqObj;
                        return yunDicDataService.findList(queryBo, token);
                    case DICDATA_INSERT:
                        YunDicDataInsertBO bo = (YunDicDataInsertBO)reqObj;
                        return yunDicDataService.insert(bo, token);
                    default:
                        return null;
                }
            }
        });
        if(!StringUtils.equals((String)resultMap.get(CommonConstant.STATUS), CommonConstant.SUCCESS)){
            String msg = (null == resultMap.get(CommonConstant.MSG_YUN) ? "工保云业务处理异常！": (String)resultMap.get(CommonConstant.MSG_YUN));
            log.error("工保云返回结果不成功，错误信息：{}", msg);
            throw new BusinessException(msg);
        }
        //过滤重复数据
        Object obj = resultMap.get(CommonConstant.DATA);
        if(null == obj){
            log.debug("【工保云】查询返回结果为空！【请求参数：{}】", JSON.toJSONString(reqObj));
            return obj;
        }
        if(funcEnum.equals(OBJECT_QUERY)){
            Set<String> repeatSet = Sets.newHashSet();
            List<Map<String, Object>> resultList = JSON.parseObject(JSON.toJSONString(obj), new TypeReference<List<Map<String, Object>>>() {});
            if(CollectionUtils.isEmpty(resultList)){
                log.debug("【工保云】查询数据为空！【请求参数：{}】", JSON.toJSONString(reqObj));
                return null;
            }
            YunObjectCodeEnum objectCodeEnum = YunObjectCodeEnum.getByCode(((ApiRequestVO)reqObj).getObjectCode());
            switch (objectCodeEnum){
                case BUSINESS_INFORMATION:
                    obj = resultList.stream().filter(u -> (((null != u.get("credit_code")) && repeatSet.add(String.valueOf(u.get("credit_code"))))))
                            .collect(Collectors.toList());
                    break;
                case XMJBXX:
                case PROJECT_ATTACH:
                case ENTERPRISE_ATTACH:
                    obj = resultList.stream().filter(u -> (((null != u.get("linkName")) && repeatSet.add(String.valueOf(u.get("linkName"))))))
                            .collect(Collectors.toList());
                    break;
                case XZQH_CASCAD:
                    obj = resultList.stream().filter(u -> (((null != u.get("value")) && repeatSet.add(String.valueOf(u.get("value"))))))
                            .collect(Collectors.toList());
                    break;
                default:
                    break;

            }
        }
        return obj;
    }

    @Override
    public Map<String, Object> validateProcess(Function<String, String> function) throws Exception {
        boolean isGetToken = false;
        //1、先从缓存获取token值，并执行相应的操作
        String token = RedisUtils.get(stringRedisTemplate, CommonConstant.LOGIN_YUN);
        Map<String, Object> resultMap = JsonUtil.bean(function.apply(token),Map.class);
        //2、获取相应的结果操作，如果是token的问题，则重新新获取token，并执行相应的操作
        Object objResult = ((null == resultMap) ? null : resultMap.get(CommonConstant.STATUS));
        if(null == objResult){
            isGetToken = true;
        }else{
            Object messageObj = resultMap.get(CommonConstant.MESSAGE);
            if((!StringUtils.equals(String.valueOf(objResult),CommonConstant.SUCCESS))
                    && (null!=messageObj)
                    && StringUtils.equals(String.valueOf(messageObj),"token invalid")){
                isGetToken = true;
            }
        }
        if(isGetToken) {
            log.debug("【工保云token失效】重新登录工保云，获取工保云token...");
            token = yunService.loginThirdparty();
            log.debug("获取工保云token【{}】完毕。", token);
            resultMap = JsonUtil.bean(function.apply(token),Map.class);
        }
        return resultMap;
    }

    @Override
    public Object sendCloud(boolean allReturn, YunSyncDataTypeEnum typeEnum, Object obj) throws Exception {
        log.debug("【工保云】{}参数：{}", typeEnum.getName(), JSON.toJSONString(obj));
        Map<String, Object> resultMap = Maps.newHashMap();
        //1、先查询请求参数是否已经存在
        Map<String, Object> requestMap =  JSONObject.parseObject(JSON.toJSONString(obj));
        Object findObj = functionSend(OBJECT_QUERY, new ApiRequestVO(){{
            setLinkId((String)requestMap.get("linkId"));
            setLinkName((String)requestMap.get("linkName"));
            setObjectCode((String)requestMap.get("objectCode"));
            setCreditCode((String)requestMap.get("credit_code"));
            setEnterpriseName((String)requestMap.get("enterpriseName"));
            setProjectName((String)requestMap.get("projectName"));
        }});
        boolean isUpdate = (StringUtils.contains(typeEnum.getCode(),"update"));
        //2、判断是更新操作还是新增操作
        if(!isUpdate){
            if(Objects.nonNull(findObj)){
                log.error("【工保云】{}待新增数据已经存在！【已存在的数据具体参数：{}】", typeEnum.getName(), JSON.toJSONString(findObj));
                throw new ParameterNullException("【工保云】"+ typeEnum.getName()+"待新增数据已经存在！");
            }
            resultMap = validateProcess(new Function<String, String>() {
                @SneakyThrows
                @Override
                public String apply(String token) {
                    return yunService.fieldInsert(token, obj);
                }
            });
        }else{
            if(Objects.isNull(findObj)){
                log.error("【工保云】{}待更新数据不存在！【待更新数据：{}】", typeEnum.getName(), JSON.toJSONString(obj));
                throw new ParameterNullException("【工保云】"+ typeEnum.getName()+"待更新数据不存在！");
            }
            resultMap = validateProcess(new Function<String, String>() {
                @SneakyThrows
                @Override
                public String apply(String token) {
                    return yunService.fieldUpdate(token, obj);
                }
            });
        }
        if(MapUtils.isEmpty(resultMap)){
            throw new ParameterNullException("【工保云】" + typeEnum.getName() + "，工保云响应结果为空！");
        }
        log.debug("【工保云】{}，工保云响应结果：{}", typeEnum.getName(), JSON.toJSONString(resultMap));
        //1、全部返回结果
        if(allReturn){
            return resultMap;
        }
        boolean objResult = (null == resultMap.get(CommonConstant.STATUS)) ? false : StringUtils.equals(String.valueOf(resultMap.get(CommonConstant.STATUS)), CommonConstant.SUCCESS);
        if(!objResult){
            String tip = "工保云" + typeEnum.getName() + "异常！";
            String errorMsg = ((null != resultMap.get(CommonConstant.MSG_YUN)) ? String.valueOf(resultMap.get(CommonConstant.MSG_YUN)) : tip);
            throw new BusinessException(errorMsg);
        }
        //2、部分返回结果
        return resultMap.get(CommonConstant.DATA);
    }
}