package com.yazuo.intelligent.ons.listener;

import com.aliyun.openservices.ons.api.MessageListener;

import java.lang.annotation.Annotation;

public interface MethodListener<T extends Annotation> extends MessageListener {
    /**
     * 取消订阅
     */
    void unsubscribe();

    /**
     * 订阅
     */
    void subscribe();
}
