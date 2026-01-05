package com.gb.qimo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gb.bean.GongBaoConfig;
import com.gb.common.constant.CommonConstant;
import com.gb.common.constant.UrlConstant;
import com.gb.common.service.CommonService;
import com.gb.qimo.entity.PotentialCustomer;
import com.gb.qimo.entity.bo.QiMoInfoBo;
import com.gb.qimo.service.QiMoService;
import com.gb.rpc.CustomerRpc;
import com.gb.utils.Json;
import com.gb.utils.enumeration.HttpType;
import com.gb.utils.enumeration.HttpWay;
import com.gb.utils.enumeration.NumericEnum;
import jodd.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 七陌云 用户信息拉取接口实现类
 *
 * @author lijh
 * @date 2021/6/2
 */
@Slf4j
@Service
public class QiMoServiceImpl implements QiMoService {
    @Resource
    private CommonService commonService;
    @Resource
    private CustomerRpc customerRpc;

    @Override
    public Map<String, Object> sendQimoyun(Map<String, Object> params, String url) throws Exception {
        String accountId = GongBaoConfig.accountId;
        String time = getDateTime();
        String authorization = Base64.encodeToString(accountId + ":" + time);
        String sig = DigestUtils.md5Hex(accountId + GongBaoConfig.apiSecret + time).toUpperCase();
        url = UrlConstant.getQimoUrl(url) + "?sig=" + sig;
        //请求七陌云查询客户资料数据接口
        String result = commonService.sendQimo(authorization, HttpWay.POST, url, params, HttpType.JSON);
        Map<String, Object> resultMap = JSON.parseObject(result, Map.class);
        if (!CommonConstant.QIMOYUN_CODE.equals(resultMap.get(CommonConstant.CODE).toString())) {
            log.error("[七陌云接口]拉取失败,resultMap={},url={},params={}", resultMap, url, params);
        }
        return resultMap;
    }

    /**
     * 把客户线索存入数据库,批量保存七陌云客户线索信息
     *
     * @param resultMap
     * @return
     */
    @Override
    public List<PotentialCustomer> saveCustomerInfo(Map<String, Object> resultMap) {
        Object dataObject = resultMap.get("data");
        if (Objects.isNull(dataObject)) {
            log.debug("[七陌云]拉取数据为空:" + resultMap);
            return new ArrayList<>();
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> dataMap = (Map<String, Object>) dataObject;
        if (NumericEnum.ZERO.getValue().equals(dataMap.get(CommonConstant.QIMOYUN_COUNT))) {
            log.debug("[七陌云]拉取数据为0:" + dataMap);
            return new ArrayList<>();
        }
        List<QiMoInfoBo> qiMoInfoBos = JSONArray.parseArray(dataMap.get("customers").toString(), QiMoInfoBo.class);
        List<PotentialCustomer> customerList = new ArrayList<>();
        log.debug("保存七陌云客户线索数据:" + qiMoInfoBos);
        for (QiMoInfoBo bo : qiMoInfoBos) {
            for (QiMoInfoBo.Phone phone : bo.getPhone()) {
                PotentialCustomer customer = new PotentialCustomer();
                customer.setMobile(phone.getTel());
                customer.setAddress(bo.getAddress());
                customer.setDangerPlantedCategoryName(bo.get了解险种());
                customer.setProvinceName(bo.getProvince());
                customer.setCityName(bo.getCity());
                customer.setSubmitTime(LocalDateTime.parse(bo.getCreateTime(), df));
                //分配allocation分配（0：未分配分配经纪人，1：已分配分配经纪人）
                customer.setAllocation(false);
                customer.setSourceQuery(bo.getSource());
                customer.setStatusQuery(bo.getStatus());
                customer.setName(bo.getName());
                customerList.add(customer);
            }
        }
        Json json1 = customerRpc.saveCustomerAll(customerList);
        if (!json1.getSuccess()) {
            log.error("批量保存七陌云客户线索信息失败");
        }
        log.debug("保存七陌云客户线索数据成功,保存数量:" + customerList.size());
        return customerList;
    }

    private String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }
}
