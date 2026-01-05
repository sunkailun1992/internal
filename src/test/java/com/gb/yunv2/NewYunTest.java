package com.gb.yunv2;

import com.alibaba.fastjson.JSON;
import com.gb.SpringTest;
import com.gb.yunv2.entity.vo.YunV2ProjectEntityVo;
import com.gb.yunv2.entity.vo.YunV2ProjectMasterDataEntityVo;
import com.gb.yunv2.utils.signature.ApiSignUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @className: com.gb.mq-> NewYunTest
 * @author: 王一飞
 * @createDate: 2021-12-22 2:15 下午
 * @description: 测试新云验签
 */
public class NewYunTest extends SpringTest {
    String priviteKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANmSk+0ZJGS7RzivSla9UhMUhg6A6VuspWG4ghhbUJVZO/tWVvO1lvXivRbHUL753sdmCcc2isaMoTuZO1NSqmO1Xko1okX+e7x2DmJKHIp4rsqYiK2wFPlQglvnH3+6P/yrzx1SUl7C5s8TUck1/r/RHbK4B04o6SyUaffuT8z7AgMBAAECgYEAoISKzG8zMXoV9pUktE/i4J7QtJyZDgCW1zzIBm5ASp9WKH0vk4gSgwAwX0DXqr4whU4bwrTTt96DCbRoV3XyrFb1DlYm3yEm2cgYukxNMtxhlL3Tpu7bL3YdGYaAIYYtgbwzvvMvT7omKI85iztweKRWQ0VjR8sdT8oE9gAsSykCQQDzrVvH6MEayi2rsesmg2Fd0bJYCp3zUuv6kbeXy+mANYbeePjPTbu/kmir/HZn4fnJfoGxCW/o0LxW56ubb0v1AkEA5JNE9RoEbRAA5SDJpRi/1u/KFH35BU88lVGixXDNaS8YFWjZAswIUViyC7ms2cs8bHgKNt4zOhSJbM1DtsePLwJAWntBvE5Z/eea48kx5uAb9GlcDsMKeYKN60HWaUAnWRsHFG7Y/KkBkRX9VfdtxA8t4DrgT2uQqWNwu9hUaWf/TQJBAIwFl2GafYmeGx5BtqUXgzWVETL6dJkHEDLcnpza3EqKGfPLldz7xkCm1/MM3FFTCgHci01PUwxKVmE7YTbQCusCQHwyerbP/DaI/eUkyE06yzbrvAJXmFR3ck+AZaQi3eiS5WGXeOMKBlVTOShsLdn82v6vKzfRggkEWeVkB+mcDaU=";
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZkpPtGSRku0c4r0pWvVITFIYOgOlbrKVhuIIYW1CVWTv7VlbztZb14r0Wx1C++d7HZgnHNorGjKE7mTtTUqpjtV5KNaJF/nu8dg5iShyKeK7KmIitsBT5UIJb5x9/uj/8q88dUlJewubPE1HJNf6/0R2yuAdOKOkslGn37k/M+wIDAQAB";

    @Test
    public void select() {
        Map<String, Object> map = new HashMap<>();
        //        map.put("resultAttributtes", new ArrayList<String>() {{
        //            add("project_internal_number");
        //            add("project_number");
        //            add("project_name");
        //            add("bid_name");
        //            add("bid_number");
        //            add("project_masterdata");
        //        }});
        map.put("dataDictionary", false);
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        Map<String, Object> paramMap = new LinkedHashMap<String, Object>() {{
            put("project_name", "国网泰州供电公司");
            //            put("project_masterdata",new LinkedHashMap<String, Object>(){{
            //                put("bid_inviter","广东省高速公路有限公司粤赣分公司");
            //                put("announcement_of_bidding_time","2020");
            //                put("construction_period","广东项目");
            //            }});
        }};
        map.put("param", paramMap);

        // 加密
        System.err.println(JSON.toJSONString(map));
        String encode01 = ApiSignUtil.encode(map, publicKey);
        System.err.println(encode01);
    }


    @Test
    public void addAndUpdateProject() {
        YunV2ProjectMasterDataEntityVo yunV2ProjectMasterDataEntityVo = new YunV2ProjectMasterDataEntityVo();
        //        yunV2ProjectMasterDataEntityVo.setInviterContactWay("17635292062");
        //        yunV2ProjectMasterDataEntityVo.setInviterContact("王联衣");

        YunV2ProjectEntityVo yunV2ProjectEntityVo = new YunV2ProjectEntityVo();
        yunV2ProjectEntityVo.setProjectName("国网泰州供电公司运检营销用房项目");
        //        yunV2ProjectEntityVo.setProjectNumber("3212012109290002-BD-001");
        yunV2ProjectEntityVo.setProjectMasterData(yunV2ProjectMasterDataEntityVo);

        //  外层字段名称
//        Map<String, Object> resultDataMap = YunV2ProjectEntityVo.getAssemblyData(yunV2ProjectEntityVo);
        //        Map<String, Object> projectMasterData = YunV2ProjectMasterDataEntityVo.getAssemblyData(yunV2ProjectMasterDataEntityVo);
        //        resultDataMap.put(YunV2Constant.PROJECT_MASTER_DATA,projectMasterData);

//        System.err.println(JSON.toJSONString(resultDataMap));
//        System.err.println(ApiSignUtil.encode(resultDataMap, publicKey));
    }
}
