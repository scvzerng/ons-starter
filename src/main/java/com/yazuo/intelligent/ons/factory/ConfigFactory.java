package com.yazuo.intelligent.ons.factory;

import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.config.AbstractConfig;
import com.yazuo.intelligent.ons.config.ListenerConfig;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.config.ProducerConfig;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;


/**
 * description
 * <p>
 * 2017-11-30 13:27
 *
 * @author scvzerng
 **/
public class ConfigFactory {

    public static <T extends AbstractConfig> T createConfig(Environment environment, Annotation annotation, OnsProperties properties){
        Map<String,Object> annotationAttrs = AnnotationUtils.getAnnotationAttributes(annotation);
        annotationAttrs.forEach((k,v)-> annotationAttrs.put(k,environment.resolvePlaceholders(v.toString())));
        new HashMap<>(annotationAttrs).forEach((k,v)->{
            if(StringUtils.isEmpty(v)){
                annotationAttrs.remove(k);
            }
        });
        AbstractConfig config;
        if(annotation.annotationType().isAssignableFrom(OnsListener.class)){
            ListenerConfig listenerConfig = new ListenerConfig();
            listenerConfig.setConsumerId((String) annotationAttrs.get("id"));
            listenerConfig.setExpression((String) annotationAttrs.get("expression"));
            listenerConfig.setTopic((String) annotationAttrs.get("topic"));
            config = listenerConfig;
        }else{
            ProducerConfig producerConfig = new ProducerConfig();
            producerConfig.setProducerId((String) annotationAttrs.get("id"));
            producerConfig.setTopic((String) annotationAttrs.get("topic"));
            producerConfig.setTag((String) annotationAttrs.get("tag"));
            producerConfig.setTimeOut((String) annotationAttrs.get("sendMsgTimeoutMillis"));
            config =  producerConfig;
        }

        config.setAccessKey(environment.resolvePlaceholders(properties.getAccessKey()));
        config.setSecretKey(environment.resolvePlaceholders(properties.getSecretKey()));
        if(!StringUtils.isEmpty(properties.getAddress())){
            config.setAddress(environment.resolvePlaceholders(properties.getAddress()));

        }
        return (T) config;
    }


}
