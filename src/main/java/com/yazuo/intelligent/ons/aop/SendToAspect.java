package com.yazuo.intelligent.ons.aop;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.ons.Key;
import com.yazuo.intelligent.ons.OnsProducerAnnotationBeanPostProcessor;
import com.yazuo.intelligent.ons.ProducerAdapter;
import com.yazuo.intelligent.ons.annotation.SendTo;
import com.yazuo.intelligent.ons.enums.ReturnType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.env.Environment;
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
    Environment environment;

    @Around(value = "@annotation(sendTo)")
    public Object round(ProceedingJoinPoint point, SendTo sendTo) throws Throwable {
        Object obj = point.proceed();
        ProducerAdapter onsOperations = OnsProducerAnnotationBeanPostProcessor.ONS_OPERATIONS_MAP.get(environment.resolvePlaceholders(sendTo.producerId()));

        if (obj == null)
            throw new NullPointerException("empty return value can not send to " + onsOperations.getProviderConfig().getTopic());
        if (obj instanceof Message) {
            onsOperations.send((Message) obj);
        } else {
            Message message = new Message();
            message.setBody(JSON.toJSONBytes(obj));
            message.setTopic(onsOperations.getProviderConfig().getTopic());
            message.setTag(onsOperations.getProviderConfig().getTag());
            if (obj instanceof Key) {
                message.setKey(((Key) obj).generateKey());
            }
            onsOperations.send(message);

        }
        if(sendTo.returnType()== ReturnType.KEEP){
            return obj;
        }else{
            return null;

        }
    }

}
