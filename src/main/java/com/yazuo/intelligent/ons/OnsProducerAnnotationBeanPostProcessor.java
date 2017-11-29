package com.yazuo.intelligent.ons;

import com.yazuo.intelligent.ons.annotation.OnsProducer;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.config.ProviderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
@Slf4j
@EnableConfigurationProperties(OnsProperties.class)
public class OnsProducerAnnotationBeanPostProcessor extends OnsAnnotationBeanPostProcessor{

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(isProcess())
                .forEach(field -> setTemplate(field,bean));
        return bean;
    }

    private Predicate<Field> isProcess(){
        return field -> field.isAnnotationPresent(OnsProducer.class);
    }



    /**
     * 反射标注了@ONSProducer设置字段的值
     * @param field
     * @param bean
     */
   private void setTemplate(Field field,Object bean){
       field.setAccessible(true);
       Assert.isAssignable(OnsOperations.class,field.getType());
       OnsProducer producer = field.getAnnotation(OnsProducer.class);
       FieldProducerAdapter fieldProducerAdapter = new FieldProducerAdapter(getConfig(producer),factoryBean);
       beans.add(fieldProducerAdapter);
       try {
           field.set(bean,fieldProducerAdapter);
       } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
       }
       field.setAccessible(false);
   }


    private ProviderConfig getConfig(OnsProducer producer){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setProducerId(environment.resolvePlaceholders(producer.producerId()));
        providerConfig.setProducerId(environment.resolvePlaceholders(producer.sendMsgTimeoutMillis()));
        providerConfig.setAccessKey(onsProperties.getAccessKey());
        providerConfig.setSecretKey(onsProperties.getSecretKey());
        providerConfig.setAddress(onsProperties.getAddress());
        return providerConfig;
    }
}
