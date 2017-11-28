package com.yazuo.intelligent.ons.factory;

import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.yazuo.intelligent.ons.config.ProviderConfig;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

public class DefaultProducerFactory implements ProducerFactory {
    @Resource
    Environment environment;

    @Override
    public Producer getProducer(ProviderConfig config) {
        config.setEnvironment(environment);
        return ONSFactory.createProducer(config.export());
    }
}
