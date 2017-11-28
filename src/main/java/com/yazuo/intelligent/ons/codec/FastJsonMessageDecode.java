package com.yazuo.intelligent.ons.codec;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;

public class FastJsonMessageDecode implements MessageDecode {


    @Override
    public Object decode(Message message, ConsumeContext context, Class<?> type) {
        if(type.isAssignableFrom(String.class)){
            return new String(message.getBody());
        }
        return JSON.parseObject(message.getBody(),type);
    }
}
