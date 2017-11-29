package com.yazuo.intelligent.ons.autoconfig;

import com.yazuo.intelligent.ons.codec.FastJsonMessageDecode;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.factory.OnsBeanFactory;
import com.yazuo.intelligent.ons.factory.OnsFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
    public OnsBeanFactory producerFactory(){
        return new OnsFactoryBean();
    }




}
