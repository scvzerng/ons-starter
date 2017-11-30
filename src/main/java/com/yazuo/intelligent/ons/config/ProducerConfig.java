package com.yazuo.intelligent.ons.config;


import com.yazuo.intelligent.ons.bean.KeyGenerator;

import static com.aliyun.openservices.ons.api.PropertyKeyConst.ProducerId;
import static com.aliyun.openservices.ons.api.PropertyKeyConst.SendMsgTimeoutMillis;

public class ProducerConfig extends AbstractConfig implements KeyGenerator {

    /**
     * 您在控制台创建的Producer ID
     */
    private String producerId;

    /**
     * 设置发送超时时间，单位毫秒
     */
    private String timeOut;

    private String topic;
    private String tag;



    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.put(ProducerId,producerId);
        this.producerId = producerId;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.put(SendMsgTimeoutMillis,timeOut);
        this.timeOut = timeOut;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String generate() {
        return producerId;
    }
}
