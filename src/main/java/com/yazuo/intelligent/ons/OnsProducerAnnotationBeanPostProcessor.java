package com.yazuo.intelligent.ons;

import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.ons.annotation.OnsProducer;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.config.ProviderConfig;
import com.yazuo.intelligent.ons.factory.ProducerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
@Slf4j
@EnableConfigurationProperties(OnsProperties.class)
public class OnsProducerAnnotationBeanPostProcessor implements BeanPostProcessor,ApplicationListener<ApplicationReadyEvent>,DisposableBean {
    private static final Map<ProviderConfig,Producer> PRODUCES = new HashMap<>();

    @Resource
    OnsProperties onsProperties;
    @Resource
    ProducerFactory producerFactory;
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(isProcess())
                .forEach(field -> setBeanTemplateField(field,bean));
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Predicate<Field> isProcess(){
        return field -> field.isAnnotationPresent(OnsProducer.class);
    }

    private ONSTemplate createTemplate(OnsProducer producer){
        ProviderConfig config = getConfig(producer);
        Producer onsProducer = producerFactory.getProducer(config);
        PRODUCES.put(config,onsProducer);
        return new ONSTemplate(onsProducer);
    }

    /**
     * 反射标注了@ONSProducer设置字段的值
     * @param field
     * @param bean
     */
   private void setBeanTemplateField(Field field,Object bean){
       field.setAccessible(true);
       OnsProducer producer = field.getAnnotation(OnsProducer.class);
       ONSTemplate template = createTemplate(producer);
       try {
           field.set(bean,template);
       } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
       }
       field.setAccessible(false);
   }
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        PRODUCES.forEach((config,producer)->{
            producer.start();
            log.info("ons:=> producer:{} started",JSON.toJSONString(config));
        });
    }

    @Override
    public void destroy() throws Exception {
        PRODUCES.forEach((config,producer)->{
            producer.shutdown();
            log.info("ons:=> producer:{} is stop",JSON.toJSONString(config));
        });
    }

    private ProviderConfig getConfig(OnsProducer producer){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setProducerId(producer.producerId());
        providerConfig.setTimeOut(onsProperties.getTimeOut().toString());
        providerConfig.setAccessKey(onsProperties.getAccessKey());
        providerConfig.setSecretKey(onsProperties.getSecretKey());
        providerConfig.setAddress(onsProperties.getAddress());
        return providerConfig;
    }
}
