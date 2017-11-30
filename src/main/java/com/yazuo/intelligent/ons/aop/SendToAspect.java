package com.yazuo.intelligent.ons.aop;

import com.aliyun.openservices.ons.api.Message;
import com.yazuo.intelligent.ons.bean.OnsOperations;
import com.yazuo.intelligent.ons.parser.OnsProducerAnnotationBeanPostProcessor;
import com.yazuo.intelligent.ons.annotation.SendTo;
import com.yazuo.intelligent.ons.codec.MessageCodec;
import com.yazuo.intelligent.ons.enums.ReturnType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * description
 * <p>
 * 2017-11-30 10:33
 *
 * @author scvzerng
 **/
@Aspect
@Component
public class SendToAspect {

    @Resource
    MessageCodec messageCodec;
    @Resource
    OnsProducerAnnotationBeanPostProcessor processor;
    @Around(value = "@annotation(sendTo)")
    @Order
    public Object round(ProceedingJoinPoint point, SendTo sendTo) throws Throwable {
        Object obj = point.proceed();
        OnsOperations operations = processor.getOnsBean(sendTo);
        Message message = messageCodec.serialze(obj,((MethodSignature)point.getSignature()).getMethod());
        operations.syncSend(message);
        if(sendTo.returnType()== ReturnType.KEEP){
            return obj;
        }else{
            return null;

        }
    }

}
