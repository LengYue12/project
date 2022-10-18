package com.lagou.rabbit;

import com.lagou.entity.SmsVo;
import com.lagou.sms.SmsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject edu-lagou
 * @Author lengy
 * @CreateTime 2022/9/19 0:50
 * @Description Mq的消费端  监听消息
 */
@Component
public class OrderRever {

    @Autowired
    private SmsService sms;

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void process(SmsVo smsVo){
        //System.out.println("得到通知，开始发送了：" + msg);
        System.out.println("监听到MQ中有新的消息："+smsVo);
        sms.sendSms(smsVo.getPhone(),smsVo.getCourseName()); //调用短信接口发送短信
    }
}
