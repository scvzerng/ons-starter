package com.yazuo.intelligent.ons.config;




import com.yazuo.intelligent.ons.bean.KeyGenerator;

import static com.aliyun.openservices.ons.api.PropertyKeyConst.ConsumerId;

public class ListenerConfig extends AbstractConfig implements KeyGenerator {
    /**
     * 主题
     */
    private String topic;
    /**
     * tag
     */
    private String expression;
    /**
     * 消费者ID
     */
    private String consumerId;



    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {

        this.put(ConsumerId,consumerId);

        this.consumerId = consumerId;
    }

    @Override
    public String generate() {
        return consumerId;
    }
}
