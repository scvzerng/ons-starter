package com.yazuo.intelligent.ons.parser;

import com.aliyun.openservices.ons.api.Admin;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Producer;
import com.yazuo.intelligent.ons.bean.KeyGenerator;
import com.yazuo.intelligent.ons.bean.OnsOperations;
import com.yazuo.intelligent.ons.bean.OnsBean;
import com.yazuo.intelligent.ons.config.AbstractConfig;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.factory.ConfigFactory;
import com.yazuo.intelligent.ons.factory.OnsBeanFactory;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


/**
 * description
 * <p>
 * 2017-11-29 14:15
 *
 * @author scvzerng
 **/
public abstract class OnsAnnotationBeanPostProcessor<T extends AbstractConfig> implements BeanPostProcessor, ApplicationListener<ApplicationReadyEvent>, DisposableBean {
    @Resource
    protected OnsBeanFactory<Consumer, Producer> factoryBean;
    @Getter
    protected Map<String, OnsBean> beans = new HashMap<>();
    @Resource
    protected OnsProperties onsProperties;
    @Resource
    protected Environment environment;

    @Override
    public void destroy() throws Exception {
        beans.values().forEach(Admin::shutdown);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Arrays.stream(bean.getClass().getDeclaredFields()).forEach(field -> {
            Annotation annotation = getFieldAnnotationClass(field);
            if (annotation != null) {
                T config = ConfigFactory.createConfig(environment, annotation, onsProperties);
                if (config instanceof KeyGenerator) {
                    KeyGenerator keyGenerator = (KeyGenerator) config;
                    if (beans.get(keyGenerator.generate()) == null) {
                        OnsBean onsBean = processFieldAnnotation(field, config);
                        if(onsBean!=null){
                            field.setAccessible(true);
                            Assert.isAssignable(OnsOperations.class, field.getType());
                            try {
                                field.set(bean, onsBean);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }

                            field.setAccessible(false);
                            beans.put(onsBean.generate(), onsBean);
                        }

                    }
                }
            }

        });
        MethodIntrospector
                .selectMethods(
                        bean.getClass(),
                        this::getAnnotationClass
                ).forEach((method, annotation) -> {
            if (annotation != null) {
                T config = ConfigFactory.createConfig(environment, annotation, onsProperties);
                if (config instanceof KeyGenerator) {
                    KeyGenerator keyGenerator = (KeyGenerator) config;
                    if (beans.get(keyGenerator.generate()) == null) {
                        OnsBean onsBean = processMethodAnnotation(bean, method, config);
                        if(onsBean!=null){
                            beans.put(onsBean.generate(), onsBean);
                        }
                    }
                } else {
                    processMethodAnnotation(bean, method, config);

                }

            }
        });
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        beans.values().forEach(Admin::start);
    }


    /**
     * 在字段上注入
     * @param field 需要注入的字段
     * @param config 生成OnsBean的配置
     * @return
     */
    protected  OnsBean processFieldAnnotation(Field field, T config){
        return null;
    }

    /**
     * 在方法上注入
     *
     * @param bean
     */
    protected abstract OnsBean processMethodAnnotation(Object bean, Method method, T config);

    protected abstract Annotation getAnnotationClass(Method method);

    protected Annotation getFieldAnnotationClass(Field field) {
        return null;
    }

}
