package com.gb.bean;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gb.utils.DataSourceUtil;
import com.gb.utils.DynamicSourceTtl;
import com.gb.utils.constants.UniversalConstant;
import jodd.util.StringUtil;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;
import org.slf4j.MDC;

/**
 * <p>
 * MQ队列配置项
 * </p>
 *
 * @author 孙凯伦
 * @since 2021-04-02
 */
@Configuration
public class RabbitConfig {

    /**风控审核推送队列名称*/
    public static final  String DUN_RISKREVIEW_Q = "riskReviewQ";

    /**退保信息推送队列名称*/
    public static final  String DUN_SURRENDER_Q = "surrenderRecordQ";

    /**关联订单信息推送队列名称*/
    public static final  String DUN_ASSOCIATEORDER_Q = "associateOrderQ";

    /**承保结果信息推送队列名称*/
    public static final  String DUN_UNDERWRITING_Q = "underWritingQ";

    /**订单完成云同步企业和项目信息队列名称*/
    public static final  String YUN_SYNCDATA_Q = "yunSyncData";

    /**风控审核同步回调结果队列名称*/
    public static final  String  DUN_RISKREVIEW_SYNC_Q= "riskReviewSyncResult";

    /**承保结果详细结果推送盾*/
    public static final  String DUN_UNDERWRITING_RESULTS__Q = "underwritingResultsQ";

    /** 保险公司出单结果推送盾*/
    public static final String DUN_INSURANCE_NOTICE__Q = "insuranceNoticeQ";

    /** 交易平台回调推送订单*/
    public static final String TRADE_CREATE_PAY_ORDER_NOTICE = "tradeCreatePayOrderNotice";
    /** 交易平台转账回调推送订单*/
    public static final String TRADE_TRANSFER_NOTICE = "tradeTransferNotice";

    /** 交易平台退汇回调推送订单*/
    public static final String REFUND_NOTICE = "refundNotice";

    /**
     * rabbitmq
     */
    @Value("${rabbitmq.host}")
    private String rabbitmqHost;
    @Value("${rabbitmq.port}")
    private Integer rabbitmqPort;
    @Value("${rabbitmq.username}")
    private String rabbitmqUsername;
    @Value("${rabbitmq.password}")
    private String rabbitmqPassword;
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    /**
     * 测试
     * @return
     */
    @Bean
    public Queue test() {
        return new Queue("test");
    }


    /**
     * 风控审核推送队列
     * @return
     */
    @Bean
    public Queue riskReviewQueue() {
        return new Queue(DUN_RISKREVIEW_Q);
    }

    /**
     * 退保信息推送队列
     * @return
     */
    @Bean
    public Queue surrenderRecordQueue() {
        return new Queue(DUN_SURRENDER_Q);
    }

    /**
     * 关联订单信息推送队列
     * @return
     */
    @Bean
    public Queue associateOrderQueue() {
        return new Queue(DUN_ASSOCIATEORDER_Q);
    }

    /**
     * 承保结果信息推送队列
     * @return
     */
    @Bean
    public Queue underWritingQueue() {
        return new Queue(DUN_UNDERWRITING_Q);
    }

    /**
     * 订单完成云同步企业和项目信息队列名称
     * @return
     */
    @Bean
    public Queue yunSyncDataQueue() {
        return new Queue(YUN_SYNCDATA_Q);
    }

    /**
     * 支付成功回调推送队列
     * @return
     */
    @Bean
    public Queue tradeCreateOrderNotice() {
        return new Queue(TRADE_CREATE_PAY_ORDER_NOTICE);
    }

    /**
     * 转账成功回调推送队列
     * @return
     */
    @Bean
    public Queue tradeTransferNotice() {
        return new Queue(TRADE_TRANSFER_NOTICE);
    }


    /**
     * 退汇成功回调推送队列
     * @return
     */
    @Bean
    public Queue tradeRefundNotice() {
        return new Queue(REFUND_NOTICE);
    }


    /**
     * rabbitmq 处理连接端口
     * @return
     */
    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitmqHost,rabbitmqPort);
        connectionFactory.setUsername(rabbitmqUsername);
        connectionFactory.setPassword(rabbitmqPassword);
        if(StringUtil.isNotBlank(applicationContext.getEnvironment().getProperty(UniversalConstant.RABBITMQ_VIRTUAL_HOST))) {
            connectionFactory.setVirtualHost(applicationContext.getEnvironment().getProperty(UniversalConstant.RABBITMQ_VIRTUAL_HOST));
        }
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setUseDirectReplyToContainer(false);
        //发送之前加一个拦截器，每次发送都会调用这个方法，方法名称已经说明了一切了
        template.setBeforePublishPostProcessors(new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //拦截逻辑添加环境变量
                message.getMessageProperties().getHeaders().put("dataSource", DynamicSourceTtl.get());
                message.getMessageProperties().getHeaders().put("traceId", MDC.get("traceId"));
                return message;
            }
        });
        template.setMessageConverter(messageConverter());
        return template;
    }


    @Bean(name = "rabbitListenerContainerFactory")
    @ConditionalOnProperty(prefix = "spring.rabbitmq.listener", name = "type", havingValue = "simple", matchIfMissing = true)
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                                                     ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //消息接收之前加拦截处理，每次接收消息都会调用，标记消息的，先存到副本变量，后续的操作数据库根据这个变量进行切换影子库
        factory.setAfterReceivePostProcessors(new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                Map header = message.getMessageProperties().getHeaders();
                //判断是动态切换影子库
                if (StringUtil.isNotBlank(String.valueOf(header.get(DataSourceUtil.DATA_SOURCE)))){
                    DynamicSourceTtl.push(String.valueOf(header.get(DataSourceUtil.DATA_SOURCE)));
                }
                if (Objects.nonNull(header.get("traceId"))){
                    MDC.put("traceId", String.valueOf(header.get("traceId")));
                }
                return message;
            }
        });
        configurer.configure(factory, connectionFactory);
        return factory;
    }


    @Bean
    public MessageConverter messageConverter(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

}