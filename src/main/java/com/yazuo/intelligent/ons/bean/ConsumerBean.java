package com.yazuo.intelligent.ons.bean;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.ons.annotation.MessageBody;
import com.yazuo.intelligent.ons.codec.MessageCodec;
import com.yazuo.intelligent.ons.config.ListenerConfig;
import com.yazuo.intelligent.ons.factory.OnsBeanFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * description
 * <p>
 * 2017-11-29 15:22
 *
 * @author scvzerng
 **/
@Slf4j
public class ConsumerBean implements OnsBean,MessageListener{
    /**
     * 实例对象
     */
    protected Object bean;
    /**
     * 调用方法
     */
    protected Method method;
    /**
     * 方法参数列表
     */
    protected List<MethodParameter> parameters;
    /**
     * 解码器
     */
    protected MessageCodec codec ;
    /**
     * 消费者
     */
    protected Consumer consumer;
    protected ListenerConfig consumerConfig;

    public ConsumerBean(Object bean, Method method, ListenerConfig consumerConfig, OnsBeanFactory<Consumer,Producer> beanFactory, MessageCodec codec) {
        this.method = method;
        this.bean = bean;
        this.codec = codec;
        this.consumerConfig = consumerConfig;
        this.consumer = beanFactory.createConsumer(consumerConfig);
        this.parameters = Stream.iterate(0, i->++i)
                .limit(method.getParameterCount())
                .map(i-> new MethodParameter(method,i))
                .collect(toList());
    }

    @Override
    public boolean isStarted() {
        return this.consumer.isStarted();
    }

    @Override
    public boolean isClosed() {
        return this.consumer.isClosed();
    }

    @Override
    public void start() {
       this.consumer.subscribe(consumerConfig.getTopic(),consumerConfig.getExpression(),this);
       this.consumer.start();
       log.info("topic {} subscribe",JSON.toJSONString(consumerConfig));
    }

    @Override
    public void shutdown() {
      this.consumer.unsubscribe(consumerConfig.getTopic());
      this.consumer.shutdown();
        log.info("topic {} unsubscribe",JSON.toJSONString(consumerConfig));
    }





    /**
     * 参数列表为一个参数的情况
     * @param parameter 参数
     * @param message 消息
     * @return
     */
    private Object[] singleParam(MethodParameter parameter,Message message){
        return new Object[]{codec.deserialze(message,null,parameter.getParameterType())};

    }

    /**
     * 参数列表为数组的情况下
     * @param message 消息
     * @param context 消费者上下文
     * @return
     */
    private Object[] multiParam(Message message,ConsumeContext context){
        return parameters.stream().map(parameter -> {
            if(parameter.hasParameterAnnotation(MessageBody.class)){
                return codec.deserialze(message,context,parameter.getParameterType());
            }

            if(parameter.getParameterType().isAssignableFrom(ConsumeContext.class)){
                return context;
            }

            if(parameter.getParameterType().isAssignableFrom(Message.class)){
                return message;
            }

            return null;
        }).toArray();
    }

    @Override
    public Action consume(Message message, ConsumeContext context) {
        try {
            if (parameters.size() == 1) {
                ReflectionUtils.invokeMethod(method, bean, singleParam(parameters.get(0), message));
            } else {
                ReflectionUtils.invokeMethod(method, bean, multiParam(message, context));
            }
            return Action.CommitMessage;
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return Action.ReconsumeLater;
        }
    }

    @Override
    public String toString() {
        return JSON.toJSONString(consumerConfig);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String generate() {
        return consumerConfig.getConsumerId();
    }


}
