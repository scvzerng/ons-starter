package com.yazuo.intelligent.ons;

import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.config.ConsumerConfig;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import com.yazuo.intelligent.ons.factory.ConsumerFactory;
import com.yazuo.intelligent.ons.listener.MethodListener;
import com.yazuo.intelligent.ons.factory.MethodListenerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.core.MethodIntrospector;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@EnableConfigurationProperties(OnsProperties.class)
public class OnsListenerAnnotationBeanPostProcessor implements BeanPostProcessor,ApplicationListener<ApplicationReadyEvent>,DisposableBean {
    @Resource
    OnsProperties onsProperties;
    /**
     * 所有的方法监听器 由@OnsListener生成
     */
    private static final Set<MethodListener> LISTENERS = new HashSet<>();
    @Resource
    private MessageDecode decode;
    @Resource
    private MethodListenerFactory methodListenerFactory;
    @Resource
    private ConsumerFactory consumerFactory;
    @SuppressWarnings("unchecked")
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        LISTENERS.addAll(MethodIntrospector.selectMethods(bean.getClass(), (MethodIntrospector.MetadataLookup<MethodListener>) method -> {
            OnsListener listener = method.getAnnotation(OnsListener.class);
            if(listener==null) return null;
            return methodListenerFactory.getListener(method,bean,decode,consumerFactory.getConsumer(getConfig(listener)),listener);
        }).values());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    /**
     * 订阅所有topic
     * @param event 应用已启动事件
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        LISTENERS.forEach(MethodListener::subscribe);
    }

    /**
     * 解除所有订阅
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        LISTENERS.forEach(MethodListener::unsubscribe);
    }


    /**
     * 创建一个消费者
     * @param listener
     * @return
     */
    private ConsumerConfig getConfig(OnsListener listener){
        ConsumerConfig consumerConfig = new ConsumerConfig();
        consumerConfig.setConsumerId(listener.consumerId());
        consumerConfig.setAccessKey(onsProperties.getAccessKey());
        consumerConfig.setSecretKey(onsProperties.getSecretKey());
        consumerConfig.setExpression(listener.expression());
        consumerConfig.setTopic(listener.topic());
        return  consumerConfig;

    }



}
