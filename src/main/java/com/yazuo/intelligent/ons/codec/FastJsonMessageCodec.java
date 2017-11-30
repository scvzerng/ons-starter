package com.yazuo.intelligent.ons.codec;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.ons.bean.KeyGenerator;
import com.yazuo.intelligent.ons.annotation.SendTo;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.lang.reflect.Method;

public class FastJsonMessageCodec implements MessageCodec {

    @Resource
    Environment environment;
    @Override
    public Object deserialze(Message message, ConsumeContext context, Class<?> type) {
        if(type.isAssignableFrom(String.class)){
            return new String(message.getBody());
        }
        return JSON.parseObject(message.getBody(),type);
    }

    @Override
    public Message serialze(Object obj,Method method) {
        SendTo sendTo = method.getAnnotation(SendTo.class);
        String topic = environment.resolvePlaceholders(sendTo.topic());
        String tag = environment.resolvePlaceholders(sendTo.tag());
        if (obj == null)
            throw new NullPointerException("empty return value can not syncSend to " +topic);
        if (obj instanceof Message) {
            return (Message) obj;
        } else {
            Message message = new Message();
            message.setBody(JSON.toJSONBytes(obj));
            message.setTopic(topic);
            message.setTag(tag);
            if (obj instanceof KeyGenerator) {
                message.setKey(((KeyGenerator) obj).generate());
            }
           return message;

        }
    }
}
