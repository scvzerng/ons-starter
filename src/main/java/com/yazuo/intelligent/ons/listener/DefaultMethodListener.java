package com.yazuo.intelligent.ons.listener;

import com.aliyun.openservices.ons.api.*;
import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import java.lang.reflect.Method;


/**
 * ons消息
 */
@Slf4j
public class DefaultMethodListener extends AbstractMethodListener<OnsListener> {
    private Environment environment;

    public DefaultMethodListener(Method method, Object bean, MessageDecode decode, Consumer consumer, OnsListener listener, Environment environment) {
        super(method, bean, decode, consumer, listener);
        this.environment = environment;
    }

    public DefaultMethodListener(Method method, Object bean, MessageDecode decode, Consumer consumer, OnsListener listener) {
        super(method, bean, decode, consumer, listener);
    }


    /**
     * 订阅
     */
    @Override
    public void subscribe() {
        this.consumer.start();
        this.consumer.subscribe(environment.resolvePlaceholders(listener.topic()),environment.resolvePlaceholders(listener.expression()),this);
        log.info("topic {} is subscribe",environment.resolvePlaceholders(listener.topic()));

    }

    /**
     * 取消订阅
     */
    @Override
    public void unsubscribe() {
        this.consumer.unsubscribe(environment.resolvePlaceholders(listener.topic()));
        this.consumer.shutdown();
        log.info("topic {} is unsubscribe",environment.resolvePlaceholders(listener.topic()));
    }
}
