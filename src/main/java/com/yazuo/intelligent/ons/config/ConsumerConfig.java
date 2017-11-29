package com.yazuo.intelligent.ons.config;

import lombok.Data;

import java.util.Properties;

import static com.aliyun.openservices.ons.api.PropertyKeyConst.ConsumerId;

@Data
public class ConsumerConfig extends AbstractConfig implements ExportOnsConfig {
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

    @Override
    public Properties export() {
        Properties properties = super.export();
        properties.put(ConsumerId,consumerId);
        return properties;
    }
}
