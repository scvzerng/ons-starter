package com.yazuo.intelligent.ons.factory;


import com.aliyun.openservices.ons.api.Consumer;
import com.yazuo.intelligent.ons.config.ConsumerConfig;

/**
 * 消费者工厂
 */
public interface ConsumerFactory {
    Consumer getConsumer(ConsumerConfig config);
}
