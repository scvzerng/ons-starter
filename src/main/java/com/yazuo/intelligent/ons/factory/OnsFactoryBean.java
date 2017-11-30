package com.yazuo.intelligent.ons.factory;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.yazuo.intelligent.ons.config.ListenerConfig;
import com.yazuo.intelligent.ons.config.ProducerConfig;

/**
 * description
 * <p>
 * 2017-11-29 14:46
 *
 * @author scvzerng
 **/
public  class OnsFactoryBean implements OnsBeanFactory<Consumer,Producer> {



    @Override
    public Consumer createConsumer(ListenerConfig config) {
        return ONSFactory.createConsumer(config);
    }

    @Override
    public Producer createProducer(ProducerConfig config) {
        return ONSFactory.createProducer(config);
    }
}
