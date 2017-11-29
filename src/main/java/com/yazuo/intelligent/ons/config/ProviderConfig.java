package com.yazuo.intelligent.ons.config;

import lombok.Data;

import java.util.Properties;

import static com.aliyun.openservices.ons.api.PropertyKeyConst.SendMsgTimeoutMillis;

@Data
public class ProviderConfig extends AbstractConfig {

    /**
     * 您在控制台创建的Producer ID
     */
    private String producerId;

    /**
     * 设置发送超时时间，单位毫秒
     */
    private String timeOut;

    @Override
    public Properties export() {
        Properties properties = super.export();
        properties.put(SendMsgTimeoutMillis,timeOut);
        return properties;
    }
}
