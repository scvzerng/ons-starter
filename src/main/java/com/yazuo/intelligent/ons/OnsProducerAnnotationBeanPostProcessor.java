package com.yazuo.intelligent.ons;

import com.yazuo.intelligent.ons.annotation.OnsProducer;
import com.yazuo.intelligent.ons.annotation.SendTo;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.config.ProviderConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Predicate;
@Slf4j
@EnableConfigurationProperties(OnsProperties.class)
public class OnsProducerAnnotationBeanPostProcessor extends OnsAnnotationBeanPostProcessor{
    public static final Map<String,ProducerAdapter> ONS_OPERATIONS_MAP = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields())
                .filter(isProcess())
                .forEach(field -> setTemplate(field,bean));
        MethodIntrospector.selectMethods(bean.getClass(), (MethodIntrospector.MetadataLookup<ProducerAdapter>) method -> {
            SendTo sendTo = method.getAnnotation(SendTo.class);
            if(sendTo==null) return null;
            ProviderConfig config = getConfig(AnnotationUtils.getAnnotationAttributes(sendTo));
            ProducerAdapter adapter = ONS_OPERATIONS_MAP.get(config.getProducerId());
            if(adapter!=null) return null;
            return new ProducerAdapter(config,factoryBean);

        }).forEach((m,a)->{
            ONS_OPERATIONS_MAP.put(a.getProviderConfig().getProducerId(),a);
            beans.add(a);
        });
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
       OnsProducer producer = field.getAnnotation(OnsProducer.class);
       ProviderConfig config = getConfig(AnnotationUtils.getAnnotationAttributes(producer));
       ProducerAdapter fieldProducerAdapter = ONS_OPERATIONS_MAP.get(config.getProducerId());;

       if(fieldProducerAdapter==null){
           fieldProducerAdapter = new ProducerAdapter(config,factoryBean);
       }
       field.setAccessible(true);
       Assert.isAssignable(OnsOperations.class,field.getType());
       beans.add(fieldProducerAdapter);
       try {
           field.set(bean,fieldProducerAdapter);
       } catch (IllegalAccessException e) {
           throw new RuntimeException(e);
       }
       field.setAccessible(false);
   }


    private ProviderConfig getConfig(Map<String,Object> attrs){
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setProducerId(environment.resolvePlaceholders((String) attrs.get("producerId")));
        providerConfig.setTopic(environment.resolvePlaceholders((String) attrs.get("topic")));
        providerConfig.setTimeOut(environment.resolvePlaceholders((String) attrs.get("sendMsgTimeoutMillis")));
        providerConfig.setAccessKey(onsProperties.getAccessKey());
        providerConfig.setSecretKey(onsProperties.getSecretKey());
        providerConfig.setAddress(onsProperties.getAddress());
        return providerConfig;
    }
}
