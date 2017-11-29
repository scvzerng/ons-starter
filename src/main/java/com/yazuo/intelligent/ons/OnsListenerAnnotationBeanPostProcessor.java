package com.yazuo.intelligent.ons;

import com.aliyun.openservices.ons.api.Admin;
import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.config.ConsumerConfig;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.MethodIntrospector;

import javax.annotation.Resource;

@Slf4j
@EnableConfigurationProperties(OnsProperties.class)
public class OnsListenerAnnotationBeanPostProcessor extends OnsAnnotationBeanPostProcessor{

    /**
     * 所有的方法监听器 由@OnsListener生成
     */
    @Resource
    private MessageDecode decode;

    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        beans.addAll(MethodIntrospector.selectMethods(bean.getClass(), (MethodIntrospector.MetadataLookup<Admin>) method -> {
            OnsListener listener = method.getAnnotation(OnsListener.class);
            if(listener==null) return null;
            MethodListenerAdapter adapter = new MethodListenerAdapter(bean,method,getConfig(listener),factoryBean);
            adapter.setDecode(decode);
            return adapter;
        }).values());
        return bean;
    }


    /**
     * 创建一个消费者
     * @param listener
     * @return
     */
    private ConsumerConfig getConfig(OnsListener listener){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setConsumerId(environment.resolvePlaceholders(listener.consumerId()));
        consumerConfig.setAccessKey(environment.resolvePlaceholders(onsProperties.getAccessKey()));
        consumerConfig.setSecretKey(environment.resolvePlaceholders((onsProperties.getSecretKey())));
        consumerConfig.setExpression(environment.resolvePlaceholders(listener.expression()));
        consumerConfig.setTopic(environment.resolvePlaceholders((listener.topic())));
        return  consumerConfig;

    }



}
