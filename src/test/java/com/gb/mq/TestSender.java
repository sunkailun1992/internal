package com.gb.mq;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.txc.parser.ast.expression.primary.function.string.X;
import com.gb.SpringTest;
import com.gb.aliyun.Oss;
import com.gb.aliyun.oss.OssUtils;
import com.gb.bean.GongBaoConfig;
import com.gb.dun.entity.bo.RoUnderwritingRefusalBO;
import com.gb.dun.service.DunPushService;
import com.gb.mq.dun.DunInsuranceInfoEvent;
import com.gb.mq.dun.DunPushEvent;
import com.gb.mq.dun.DunRiskSysReviewEvent;
import com.gb.mq.dun.DunRoUnderwritingRefusalEvent;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.gb.dun.enmus.DunPushTypeEnum.DUN_RISKVIEW_TYPE;
import static com.gb.dun.enmus.DunPushTypeEnum.DUN_UNDERWRITING_TYPE;

/**
 * @ClassName TestSender
 * @Description rabbitmq测试发送
 * @Author 孙凯伦
 * @Mobile 13777579028
 * @Email 376253703@qq.com
 * @Time 2021/4/21 10:51 上午
 */
public class TestSender extends SpringTest {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private DunPushService dunPushService;

    @Test
    public void send() throws Exception {
        List<RoUnderwritingRefusalBO> data = new ArrayList<>();
        String typeName = data.getClass().getTypeName();
        String vo = typeName.substring((typeName.lastIndexOf(".") + 1));
        vo = vo.substring(0, 1).toLowerCase() + vo.substring(1);
        System.out.println("--------" + vo);

        String pdfUrl = "http://aegis.oss-cn-hangzhou.aliyuncs.com/pdf/ly/20220311_87a288d998294ba3a585b31cd553f941.pdf?Expires=1646962785&OSSAccessKeyId=LTAI5tHXuWHCCgYrfSpARmUn&Signature=o9ki4L1NcnECyg%2Br5FCpfjx0DDc%3D";
        //File file = HttpUtil.downloadFileFromUrl(pdfUrl, StringUtils.EMPTY);
        //String s = "risk/" + file.getName();
        //String url = OssUtils.uploadFileReturnUrl(file.getPath(), s);
        //FileUtil.del("risk/20210610_7aec19f223b24c3cac72a11c8ce0dab1.pdf");
        //System.out.println("--------" + url);
        //String context = "hello " + new Date();
        //System.out.println("Sender : " + context);
        //rabbitTemplate.convertAndSend("test", context);
    }

    @Test
    public void replaceOneTest() throws Exception {

        // 需要覆盖的oss地址
        //String path = "https://gongbaojinji.oss-cn-hangzhou.aliyuncs.com/pdf/tainan/bid/egurantee/20211111100006&1458615158234030081.pdf";
        String path = "http://aegis.oss-cn-hangzhou.aliyuncs.com/pdf/ly/20210610_7aec19f223b24c3cac72a11c8ce0dab1.pdf?Expires=1646904471&OSSAccessKeyId=LTAI5tHXuWHCCgYrfSpARmUn&Signature=LYdWMj9nmHoOY3KYo3JkCIlodIY%3D";

        // 原地址
        String pdfUrl = "https://tianaw.95505.cn/tacapp/tiananapp/epolicy/eCarPolicyDownloadChannel?policyNo=6016434184006210089587&identifyNumber=913303815826626795&channelCode=PD-NCPI01&signature=LoffCwyqau6yvcfFQUYJ0DwX3F0MsT7YuKlAjISHaAwWw5dK0u58pK8W4KqGlzeB&guaranteeType=1";
        File file = HttpUtil.downloadFileFromUrl(pdfUrl, StringUtils.EMPTY);

        path = path.contains(Oss.domain) ? StringUtils.removeStart(path, Oss.domain + "/risk/") : path;
        System.out.println("------path:" + path);
        System.out.println("========" + OssUtils.uploadFileReturnUrl(file.getPath(), path));
        FileUtil.del(file.getPath());

    }

    @Test
    public void test() {
        DunRiskSysReviewEvent dunPushEvent = new DunRiskSysReviewEvent();
        dunPushEvent.setPolicyHolderId("1496772097928007681");
        dunPushEvent.setCreateName("sys");
        dunPushEvent.setPushType(DUN_RISKVIEW_TYPE);
        test13(dunPushEvent);

        //Object objectBO = new RoUnderwritingRefusalBO();
        //BeanUtils.copyProperties(dunPushEvent, objectBO);
        //String typeName = objectBO.getClass().getTypeName();
        //String vo = typeName.substring((typeName.lastIndexOf(".")+1));
        //vo = vo.substring(0,1).toLowerCase() + vo.substring(1);
        //System.out.println("======="+vo);
        //System.out.println("------------"+objectBO);
    }

    public void test13(DunPushEvent dunPushEvent) {
        dunPushService.dealBusiness(dunPushEvent);
    }

}

