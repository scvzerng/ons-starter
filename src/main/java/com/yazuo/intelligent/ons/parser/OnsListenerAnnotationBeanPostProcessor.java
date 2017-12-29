package com.yazuo.intelligent.ons.parser;

import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.bean.ConsumerBean;
import com.yazuo.intelligent.ons.config.ListenerConfig;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.codec.MessageCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Slf4j
@EnableConfigurationProperties(OnsProperties.class)
public class OnsListenerAnnotationBeanPostProcessor extends OnsAnnotationBeanPostProcessor<ListenerConfig>{

    /**
     * 所有的方法监听器 由@OnsListener生成
     */
    @Resource
    private MessageCodec decode ;


    @Override
    protected ConsumerBean processMethodAnnotation(Object bean, Method method, ListenerConfig config) {
        return new ConsumerBean(bean,method, config,factoryBean,decode);
    }

    @Override
    protected Annotation getAnnotationClass(Method method) {
        return method.getAnnotation(onsProperties.getListenerAnnotation());
    }


}
