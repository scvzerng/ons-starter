package com.yazuo.intelligent.ons.factory;

import com.aliyun.openservices.ons.api.Producer;
import com.yazuo.intelligent.ons.config.ProviderConfig;

/**
 * 生产者工厂
 */
public interface ProducerFactory {
    Producer getProducer(ProviderConfig config);
}
