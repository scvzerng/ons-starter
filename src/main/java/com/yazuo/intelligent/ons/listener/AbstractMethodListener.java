package com.yazuo.intelligent.ons.listener;

import com.aliyun.openservices.ons.api.*;
import com.yazuo.intelligent.ons.annotation.MessageBody;
import com.yazuo.intelligent.ons.codec.MessageDecode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
@Slf4j
public abstract class AbstractMethodListener<T extends Annotation> implements MethodListener<T> {
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
    protected MessageDecode decode;
    /**
     * 消费者
     */
    protected Consumer consumer;
    /**
     * 监听器注解
     */
    protected T listener;

    public AbstractMethodListener(Method method, Object bean, MessageDecode decode, Consumer consumer, T listener) {
        this.decode = decode;
        this.method = method;
        this.bean = bean;
        this.listener = listener;
        this.consumer = consumer;
        this.parameters = Stream.iterate(0, i->++i)
                .limit(method.getParameterCount())
                .map(i-> new MethodParameter(method,i))
                .collect(toList());

    }





    /**
     * 参数列表为一个参数的情况
     * @param parameter 参数
     * @param message 消息
     * @return
     */
    private Object[] singleParam(MethodParameter parameter,Message message){
        return new Object[]{decode.decode(message,null,parameter.getParameterType())};

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
                return decode.decode(message,context,parameter.getParameterType());
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

}
