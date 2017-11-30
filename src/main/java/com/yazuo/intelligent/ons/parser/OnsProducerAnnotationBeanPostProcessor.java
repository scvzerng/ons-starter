package com.yazuo.intelligent.ons.parser;

import com.yazuo.intelligent.ons.bean.OnsOperations;
import com.yazuo.intelligent.ons.annotation.OnsProducer;
import com.yazuo.intelligent.ons.annotation.SendTo;
import com.yazuo.intelligent.ons.bean.OnsBean;
import com.yazuo.intelligent.ons.bean.ProducerBean;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.config.ProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Slf4j
@EnableConfigurationProperties(OnsProperties.class)
public class OnsProducerAnnotationBeanPostProcessor extends OnsAnnotationBeanPostProcessor<ProducerConfig>{


    /**
     * 在字段上注入
     * @param bean
     */
    @Override
    public OnsBean processFieldAnnotation(Field field, ProducerConfig config){
        return new ProducerBean(config,factoryBean);


    }

    @Override
    protected OnsBean processMethodAnnotation(Object bean, Method method, ProducerConfig config) {
            return new ProducerBean(config,factoryBean);
    }

    @Override
    protected Annotation getAnnotationClass(Method method) {
        return method.getAnnotation(SendTo.class);
    }

    @Override
    protected Annotation getFieldAnnotationClass(Field field) {
        return field.getAnnotation(OnsProducer.class);
    }

    public OnsOperations getOnsBean(SendTo sendTo){
        return (OnsOperations) beans.get(environment.resolvePlaceholders(sendTo.id()));
    }
}
