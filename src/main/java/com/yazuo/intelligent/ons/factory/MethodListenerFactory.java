package com.yazuo.intelligent.ons.factory;


import com.aliyun.openservices.ons.api.Consumer;
import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import com.yazuo.intelligent.ons.listener.MethodListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public interface MethodListenerFactory<T extends Annotation> {
    /**
     * 获取监听器
     * @return
     */
    MethodListener getListener(Method method, Object bean, MessageDecode decode, Consumer consumer, T listener);

}
