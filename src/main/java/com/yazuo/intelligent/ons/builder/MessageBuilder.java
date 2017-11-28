package com.yazuo.intelligent.ons.builder;

import com.aliyun.openservices.ons.api.Message;

import java.util.Properties;

/**
 * 消息构建器
 */
public class MessageBuilder  {
    private Message message;

    public MessageBuilder() {
        this.message = new Message();
    }


    public MessageBuilder setBody(byte[] body) {
        message.setBody(body);
        return this;
    }
    public MessageBuilder setBornHost(String value) {
        message.setBornHost(value);
        return this;
    }

    public MessageBuilder setBornTimestamp(long value) {
        message.setBornTimestamp(value);
        return this;
    }

    public MessageBuilder setKey(String key) {
        message.setKey(key);
        return this;
    }

    public MessageBuilder setMsgID(String msgid) {
        message.setMsgID(msgid);
        return this;
    }

    public MessageBuilder setReconsumeTimes(int value) {
        message.setReconsumeTimes(value);
        return this;
    }

    public MessageBuilder setShardingKey(String value) {
        message.setShardingKey(value);
        return this;
    }

    public MessageBuilder setStartDeliverTime(long value) {
        message.setStartDeliverTime(value);
        return this;
    }

    public MessageBuilder setTag(String tag) {
        message.setTag(tag);
        return this;
    }

    public MessageBuilder setTopic(String topic) {
        message.setTopic(topic);
        return this;
    }

    public MessageBuilder setUserProperties(Properties userProperties) {
        message.setUserProperties(userProperties);
        return this;
    }

    public Message build(){
        return message;
    }
}
