package com.yazuo.intelligent.ons;

import com.aliyun.openservices.ons.api.Admin;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Producer;
import com.yazuo.intelligent.ons.config.OnsProperties;
import com.yazuo.intelligent.ons.factory.OnsBeanFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;


/**
 * description
 * <p>
 * 2017-11-29 14:15
 *
 * @author scvzerng
 **/
public abstract class OnsAnnotationBeanPostProcessor implements BeanPostProcessor,ApplicationListener<ApplicationReadyEvent>,DisposableBean {
    @Resource
    protected OnsBeanFactory<Consumer,Producer> factoryBean;
    protected Set<Admin> beans = new HashSet<>();
    @Resource
    protected OnsProperties onsProperties;
    @Resource
    protected Environment environment;

    @Override
    public void destroy() throws Exception {
        beans.forEach(Admin::shutdown);
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }



    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        beans.forEach(Admin::start);
    }



}
