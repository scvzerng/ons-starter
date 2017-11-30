package com.yazuo.intelligent.ons.bean;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.yazuo.intelligent.ons.config.ProducerConfig;
import com.yazuo.intelligent.ons.factory.OnsBeanFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ProducerBean implements OnsOperations,OnsBean {
    private Producer producer;
    @Getter
    private ProducerConfig providerConfig;

    public ProducerBean(ProducerConfig providerConfig, OnsBeanFactory<com.aliyun.openservices.ons.api.Consumer,Producer> onsBeanFactory) {
        this.providerConfig = providerConfig;
        this.producer = onsBeanFactory.createProducer(providerConfig);
    }


    @Override
    public SendResult syncSend(Message message) {
        return producer.send(message);
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

    @Override
    public String generate() {
        return providerConfig.getProducerId();
    }
}
