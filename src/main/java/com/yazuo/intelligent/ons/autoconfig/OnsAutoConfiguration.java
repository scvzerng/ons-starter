package com.yazuo.intelligent.ons.autoconfig;

import com.yazuo.intelligent.ons.codec.FastJsonMessageDecode;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import com.yazuo.intelligent.ons.factory.ConsumerFactory;
import com.yazuo.intelligent.ons.factory.DefaultConsumerFactory;
import com.yazuo.intelligent.ons.factory.DefaultProducerFactory;
import com.yazuo.intelligent.ons.factory.ProducerFactory;
import com.yazuo.intelligent.ons.factory.DefaultMethodListenerFactory;
import com.yazuo.intelligent.ons.factory.MethodListenerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnsAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MessageDecode messageDecode(){
        return new FastJsonMessageDecode();
    }

    @Bean
    @ConditionalOnMissingBean
    public MethodListenerFactory methodListenerFactory(){
        return new DefaultMethodListenerFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ConsumerFactory consumerFactory(){
        return new DefaultConsumerFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public ProducerFactory producerFactory(){
        return new DefaultProducerFactory();
    }
}
