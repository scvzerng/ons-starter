package com.yazuo.intelligent.ons.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.shade.com.alibaba.fastjson.annotation.JSONField;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("ons")
public class OnsProperties {
    /**
     * AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    @JSONField(name = PropertyKeyConst.AccessKey)
    private String accessKey;
    /**
     * SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    @JSONField(name = PropertyKeyConst.SecretKey)
    private String secretKey;
    /**
     *设置 TCP 接入域名
     */
    @JSONField(name = PropertyKeyConst.ONSAddr)
    private String address;
    /**
     * 您在控制台创建的Producer ID
     */
    @JSONField(name = PropertyKeyConst.ProducerId)
    private String producerId;
    /**
     * 您在控制台创建的 Consumer ID
     */
    @JSONField(name = PropertyKeyConst.ConsumerId)
    private String consumerId;
    /**
     * 设置发送超时时间，单位毫秒
     */
    @JSONField(name = PropertyKeyConst.SendMsgTimeoutMillis)
    private Integer timeOut = 3000;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProducerId() {
        return producerId;
    }

    public void setProducerId(String producerId) {
        this.producerId = producerId;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
}
