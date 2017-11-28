package com.yazuo.intelligent.ons.factory;

import com.aliyun.openservices.ons.api.Consumer;
import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import com.yazuo.intelligent.ons.factory.MethodListenerFactory;
import com.yazuo.intelligent.ons.listener.DefaultMethodListener;
import com.yazuo.intelligent.ons.listener.MethodListener;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.lang.reflect.Method;

public class DefaultMethodListenerFactory implements MethodListenerFactory<OnsListener> {
    @Resource
    Environment environment;

    @Override
    public MethodListener getListener(Method method, Object bean, MessageDecode decode, Consumer consumer, OnsListener listener) {
        return new DefaultMethodListener(method,bean,decode,consumer,listener,environment);
    }
}
