package com.yazuo.intelligent.ons.factory;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.yazuo.intelligent.ons.config.ConsumerConfig;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

public class DefaultConsumerFactory implements ConsumerFactory {
    @Resource
    Environment environment;
    @Override
    public Consumer getConsumer(ConsumerConfig config) {
        config.setEnvironment(environment);
        return ONSFactory.createConsumer(config.export());
    }
}
