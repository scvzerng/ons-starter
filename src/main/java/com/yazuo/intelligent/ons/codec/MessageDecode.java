package com.yazuo.intelligent.ons.codec;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
public interface MessageDecode {

    /**
     * 解码
     * @param message
     * @param context
     * @return
     */
    Object decode(Message message, ConsumeContext context, Class<?> type);
}
