package com.yazuo.intelligent.ons.autoconfig;

import com.yazuo.intelligent.ons.codec.FastJsonMessageCodec;
import com.yazuo.intelligent.ons.codec.MessageCodec;
import com.yazuo.intelligent.ons.factory.OnsBeanFactory;
import com.yazuo.intelligent.ons.factory.OnsFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OnsAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MessageCodec messageDecode(){
        return new FastJsonMessageCodec();
    }
    @Bean
    @ConditionalOnMissingBean
    public OnsBeanFactory producerFactory(){
        return new OnsFactoryBean();
    }




}
