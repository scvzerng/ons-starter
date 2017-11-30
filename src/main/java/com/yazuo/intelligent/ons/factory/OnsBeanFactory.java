package com.yazuo.intelligent.ons.factory;

import com.yazuo.intelligent.ons.config.ListenerConfig;
import com.yazuo.intelligent.ons.config.ProducerConfig;

/**
 * description
 * <p>
 * 2017-11-29 14:35
 *
 * @author scvzerng
 **/
public interface  OnsBeanFactory<C,P>  {

    C createConsumer(ListenerConfig config);
    P createProducer(ProducerConfig config);

}
