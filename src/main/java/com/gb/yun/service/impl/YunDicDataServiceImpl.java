package com.gb.yun.service.impl;

import com.gb.common.constant.UrlConstant;
import com.gb.common.service.CommonService;
import com.gb.utils.enumeration.HttpWay;
import com.gb.yun.entity.bo.YunDicDataInsertBO;
import com.gb.yun.entity.bo.YunDicDataQueryBO;
import com.gb.yun.service.YunDicDataService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class YunDicDataServiceImpl implements YunDicDataService {

    private CommonService commonService;

    @Override
    public String findList(YunDicDataQueryBO bo, String token) throws Exception{
        String url =  UrlConstant.getYunUrl(UrlConstant.GBY_DICDATA_LIST_CXURL) + "?dataCode=" + bo.getDataCode();
        StringBuilder sbf = new StringBuilder(url);
        sbf.append("?dataCode=" + bo.getDataCode());
        sbf.append("&masterDataId=" + bo.getMasterDataId());
        if(StringUtils.isNotBlank(bo.getCategoryId())){
            sbf.append("&categoryId=" + bo.getCategoryId());
        }
        if(StringUtils.isNotBlank(bo.getLinkId())){
            sbf.append("&linkId=" + bo.getLinkId());
        }
        if(null != bo.getIsMasterData()){
            sbf.append("&isMasterData=" + bo.getIsMasterData());
        }
        return commonService.send(token, HttpWay.GET, sbf.toString(), null , null, null);
    }

    @Override
    public String insert(YunDicDataInsertBO bo, String token) throws Exception {
        String url =  UrlConstant.getYunUrl(UrlConstant.GBY_DICDATA_LIST_INSERTURL) + "?dataCode=" + bo.getDataCode();
        StringBuilder sbf = new StringBuilder(url);
        sbf.append("?dataCode=" + bo.getDataCode());
        sbf.append("&masterDataId=" + bo.getMasterDataId());
        return commonService.send(token, HttpWay.GET, sbf.toString(), null , null, null);
    }
}