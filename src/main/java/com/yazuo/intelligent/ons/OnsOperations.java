package com.yazuo.intelligent.ons;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.yazuo.intelligent.ons.builder.MessageBuilder;

import java.util.function.Consumer;

public interface OnsOperations {
    SendResult send(Message message);
    SendResult send(MessageBuilder builder);
    void send(String topic,String message);
    void send(String topic, String message, Consumer<SendResult> resultConsumer);
}
