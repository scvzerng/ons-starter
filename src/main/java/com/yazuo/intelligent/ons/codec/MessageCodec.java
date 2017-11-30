package com.yazuo.intelligent.ons.codec;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

import java.lang.reflect.Method;

public interface MessageCodec {

    /**
     * 解码
     * @param message
     * @param context
     * @return
     */
    Object deserialze(Message message, ConsumeContext context, Class<?> type);

    Message serialze(Object obj, Method method);
}
