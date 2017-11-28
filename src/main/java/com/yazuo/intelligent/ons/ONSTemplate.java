package com.yazuo.intelligent.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.yazuo.intelligent.ons.builder.MessageBuilder;

import java.util.function.Consumer;


public class ONSTemplate implements OnsOperations {
    private Producer producer;

    public ONSTemplate(Producer producer) {
        this.producer = producer;
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
}
