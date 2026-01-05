package com.gb.yun.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.gb.bean.GongBaoConfig;
import com.gb.common.constant.CommonConstant;
import com.gb.common.constant.UrlConstant;
import com.gb.common.service.CommonService;
import com.gb.utils.JsonUtil;
import com.gb.utils.RedisUtils;
import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;
import com.gb.utils.exception.*;
import com.gb.yun.entity.vo.*;
import com.gb.yun.service.YunService;
import com.google.common.collect.Maps;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


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
public class YunServiceImpl implements YunService {

    private StringRedisTemplate stringRedisTemplate;

    private CommonService commonService;

    /**通用时间格式*/
    private static final String DATETIMEFMT = "yyyy/MM/dd HH:mm:ss";
    private static final String DATEFMT = "yyyy/MM/dd";

    @Override
    public String loginThirdparty() throws Exception {
        //1、组织请求参数
        String url = GongBaoConfig.cloudUrl+ UrlConstant.GBY_LOGIN_URL;
        Map<String, Object> map = Maps.newHashMap();
        map.put("loginname", GongBaoConfig.cloudUsername);
        map.put("loginpass", GongBaoConfig.cloudPassword);
        map.put("code", GongBaoConfig.cloudPlatCode);
        //2、发送请求
        String responseJson = commonService.send(null, HttpWay.POST, url, map, HttpType.JSON, null);
        Map<String, Object> resultMap = JSON.parseObject(responseJson, Map.class);
        if(!StringUtils.equals(String.valueOf(resultMap.get(CommonConstant.STATUS)),CommonConstant.SUCCESS)){
            throw new UserException("工保云登录失败：" + resultMap.get(CommonConstant.MESSAGE));
        }
        //3、获取token，并设置到缓存中
        String token = (String)resultMap.get(CommonConstant.DATA);
        if(StringUtils.isBlank(token)){
            throw new ParameterNullException("工保云返回的TOKEN为空！");
        }
        if(StringUtils.isNotBlank(RedisUtils.get(stringRedisTemplate, CommonConstant.LOGIN_YUN))){
            RedisUtils.delete(stringRedisTemplate,  CommonConstant.LOGIN_YUN);
        }
        boolean b = stringRedisTemplate.opsForValue().setIfAbsent(CommonConstant.LOGIN_YUN, token, 36, TimeUnit.HOURS);
        if (!b) {
            //抛出异常
            throw new PreventRepeatException("工保云重复登录!");
        }
        return token;
    }

    @Override
    public String findAll(ApiRequestVO dto) throws Exception {
        StringBuilder url = new StringBuilder(UrlConstant.getYunUrl(UrlConstant.GBY_OBJECT_CXURL));
        url.append("?");
        url.append("objectCode=" + dto.getObjectCode());
        if(null != dto.getPageSize() && null != dto.getOffset()) {
            url.append("&pageSize="+ dto.getPageSize());
            url.append("&offset="+ dto.getOffset());
        }
        if(StringUtils.isNotBlank(dto.getProjectName())){
            url.append("&project_name=" + dto.getProjectName());
        }

        if(StringUtils.isNotBlank(dto.getEnterpriseName())){
            url.append("&enterpris_name=" + dto.getEnterpriseName());
        }

        if(StringUtils.isNotBlank(dto.getCreditCode())){
            url.append("&credit_code=" + dto.getCreditCode());
        }

        if(StringUtils.isNotBlank(dto.getLinkName())){
            url.append("&linkName="+dto.getLinkName());
        }
        //企业ID
        if(StringUtils.isNotBlank(dto.getLinkId())) {
            url.append("&linkId="+ dto.getLinkId());
        }
        //主数据ID
        if(StringUtils.isNotBlank(dto.getMasterDataId())){
            url.append("&masterDataId="+ dto.getMasterDataId());
        }
        //分类ID
        if(StringUtils.isNotBlank(dto.getCategoryId())){
            url.append("&categoryId="+ dto.getCategoryId());
        }
        //业务对象ID
        if(StringUtils.isNotBlank(dto.getBusinessObjectId())) {
            url.append("&id="+ dto.getBusinessObjectId());
        }
        //-------------------------------------------------业务对象：bo_qhdm---------------------------------------------------
        if(StringUtils.isNotBlank(dto.getFatherId())){
            url.append("&fatherId=" + dto.getFatherId());
        }
        if(StringUtils.isNotBlank(dto.getValue())){
            url.append("&value=" + dto.getValue());
        }
        if(StringUtils.isNotBlank(dto.getName())){
            url.append("&name=" + dto.getName());
        }
        return commonService.send( dto.getToken(), HttpWay.GET, url.toString(), null , null, null);
    }

    @Override
    public String fieldInsert(String token, Object dto) throws Exception {
        String url =  UrlConstant.getYunUrl(UrlConstant.GBY_APIZD_XZURL);
        return sendYun(dto, url, HttpWay.POST, token);
    }

    @Override
    public String fieldUpdate(String token, Object dto) throws Exception {
        String url =  UrlConstant.getYunUrl(UrlConstant.GBY_APIZD_GXURL);
        return sendYun(dto, url, HttpWay.PUT, token);
    }

    @Override
    public String treeData(String token) throws Exception {
        String url =  UrlConstant.getYunUrl(UrlConstant.GBY_XZQH_TREE);
        return commonService.send(token, HttpWay.GET, url, null , null, null);
    }

    /**
     * 组织数据到工保云进行处理
     *
     * @param dto: 请求参数
     * @param url: 请求路径
     * @param httpWay: 请求方式
     * @param token: 请求token
     * @return String
     * @author sunx
     * @since 2021-03-18
     */
    private String sendYun(Object dto, String url, HttpWay httpWay, String token) throws Exception {
        log.debug("发送数据到工保云处理【请求方式：{}，请求参数：{}，请求路径：{}】", httpWay.toString(), JSON.toJSONString(dto), url);
        Map<String, Object> map = Maps.newHashMap();
        if(dto instanceof FieldEnterpriseRequestVO){
            FieldEnterpriseRequestVO txtEnterpriseVO = buildTxtEnterpriseVO((FieldEnterpriseRequestVO) dto);
            organzieFieldEnterpriseMap(txtEnterpriseVO, map);
        }else if(dto instanceof FieldProjectRequestVO){
            FieldProjectRequestVO txtProjectVO = (FieldProjectRequestVO) dto;
            organzieFieldProjectMap(txtProjectVO, map);
        }else if(dto instanceof FileProjectRequestVO || dto instanceof FileEnterpriseRequestVO){
            map = JsonUtil.bean(JSON.toJSONString(dto), Map.class);
        }else {
            throw new BusinessException("非企业或项目类型，请重新发送数据进行处理！");
        }
        return commonService.send( token, httpWay, url, map , HttpType.JSON, null);
    }

    /**
     * 组织企业字段内容
     *
     * @param dto:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    private FieldEnterpriseRequestVO buildTxtEnterpriseVO(FieldEnterpriseRequestVO dto) throws DateException {
        //注册资本设置值
        BigDecimal regCapital = dto.getRegisteredCapital();
        if(null !=  regCapital){
            dto.setRegCapital(regCapital);
        }
        //法人日期起止
        String dateStart = StringUtils.EMPTY;
        String dateEnd = StringUtils.EMPTY;
        Date farenDateStart = dto.getFarenDateStart();
        Date farenDateEnd = dto.getFarenDateEnd();
        if((null != farenDateStart) && (null != farenDateEnd)){
            if(farenDateStart.after(farenDateEnd)){
                throw new DateException("法定代表人身份证有效期起不能大于法定代表人身份证有效期止");
            }
            dateStart = DateUtil.format(farenDateStart,DATETIMEFMT);
            dateEnd = DateUtil.format(farenDateEnd, DATETIMEFMT);
            dto.setFarenDate(dateStart + "-" + dateEnd);
        }

        //营业期限起止
        Date businesStermStart = dto.getBusinesStermStart();
        Date businesStermEnd = dto.getBusinesStermEnd();
        if((null != businesStermStart) && (null != businesStermEnd)){
            if(businesStermStart.after(businesStermEnd)){
                throw new DateException("营业期限起不能大于营业期限止");
            }
            dateStart = DateUtil.format(businesStermStart, DATETIMEFMT);
            dateEnd = DateUtil.format(businesStermEnd, DATETIMEFMT);
            dto.setBusinesSterm(dateStart + "-" + dateEnd);
        }
        return dto;
    }

    /**
     * 组织企业字段Map
     *
     * @param requestDTO:
     * @param map:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    private void organzieFieldEnterpriseMap(FieldEnterpriseRequestVO requestDTO, Map<String, Object> map) {
        Map<String, Object> enterpriseMap = JsonUtil.bean(JSON.toJSONString(requestDTO),Map.class);
        //注册时间
        if(null != requestDTO.getRegistrationTime()){
            enterpriseMap.put("Registration_time", DateUtil.format(requestDTO.getRegistrationTime(), DATEFMT));
        }
        //成立时间
        if(null != requestDTO.getEstablishTime()){
            enterpriseMap.put("establish_time", DateUtil.format(requestDTO.getEstablishTime(), DATEFMT));
        }
        //核准日期
        if(null != requestDTO.getApprovalDate()){
            enterpriseMap.put("Approval_date", DateUtil.format(requestDTO.getApprovalDate(), DATEFMT));
        }
        map.putAll(enterpriseMap);
    }

    /**
     * 组织项目字段Map
     *
     * @param requestDTO:
     * @param map:
     * @return com.utils.Json
     * @author sunx
     * @since 2021-03-18
     */
    private void organzieFieldProjectMap(FieldProjectRequestVO requestDTO, Map<String, Object> map) {
        Map<String, Object> projectMap = JsonUtil.bean(JSON.toJSONString(requestDTO), Map.class);
        //TODO：后期需要改成xmbh
        projectMap.put("xmbh", requestDTO.getProjectName());
        //开标日期
        if(null != requestDTO.getBidOpenDate()){
            projectMap.put("bid_open_date", DateUtil.format(requestDTO.getBidOpenDate(), DATEFMT));
        }
        //发布日期
        if(null != requestDTO.getPublicDate()){
            projectMap.put("public_date", DateUtil.format(requestDTO.getPublicDate(), DATEFMT));
        }
        //计划开工日期
        if(null != requestDTO.getStartDate()){
            projectMap.put("start_date", DateUtil.format(requestDTO.getStartDate(), DatePattern.NORM_DATE_PATTERN));
        }
        //计划竣工日期
        if(null != requestDTO.getEndDate()){
            projectMap.put("end_date", DateUtil.format(requestDTO.getEndDate(), DatePattern.NORM_DATE_PATTERN));
        }
        //合同签订日期
        if(null != requestDTO.getContractDate()){
            projectMap.put("contract_date",DateUtil.format(requestDTO.getContractDate(), DatePattern.NORM_DATE_PATTERN));
        }
        //开标时间
        if(null != requestDTO.getBidOpenTime()){
            projectMap.put("bid_open_time",DateUtil.format(requestDTO.getBidOpenTime(), DatePattern.NORM_DATE_PATTERN));
        }
        //保证金缴纳截止时间
        if(null != requestDTO.getCloseTime()){
            projectMap.put("close_time",DateUtil.format(requestDTO.getCloseTime(), DatePattern.NORM_DATE_PATTERN));
        }
        map.putAll(projectMap);
    }
}