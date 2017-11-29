package com.yazuo.intelligent.ons;

import com.aliyun.openservices.ons.api.Admin;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.ons.builder.MessageBuilder;
import com.yazuo.intelligent.ons.config.ProviderConfig;
import com.yazuo.intelligent.ons.factory.OnsBeanFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class FieldProducerAdapter implements OnsOperations,Admin {
    private Producer producer;
    private ProviderConfig providerConfig;

    public FieldProducerAdapter(ProviderConfig providerConfig, OnsBeanFactory<com.aliyun.openservices.ons.api.Consumer,Producer> onsBeanFactory) {
        this.providerConfig = providerConfig;
        this.producer = onsBeanFactory.createProducer(providerConfig);
    }


    @Override
    public SendResult send(Message message) {
        return producer.send(message);
    }

    @Override
    public SendResult send(MessageBuilder builder) {
        return producer.send(builder.build());
    }

    @Override
    public void send(String topic, String message) {
        send(topic,message,null);
    }

    @Override
    public void send(String topic, String message, Consumer<SendResult> resultConsumer) {

        SendResult result = producer.send(new MessageBuilder().setTopic(topic).setBody(message.getBytes()).build());
        if(resultConsumer!=null) resultConsumer.accept(result);

    }

    @Override
    public boolean isStarted() {
        return producer.isStarted();
    }

    @Override
    public boolean isClosed() {
        return producer.isClosed();
    }

    @Override
    public void start() {
        producer.start();
        log.info("ons {} started", JSON.toJSONString(providerConfig));
    }

    @Override
    public void shutdown() {
        producer.shutdown();
        log.info("ons {} shutdown", JSON.toJSONString(providerConfig));
    }
}
