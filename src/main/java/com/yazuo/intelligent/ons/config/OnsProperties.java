package com.yazuo.intelligent.ons.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.shade.com.alibaba.fastjson.annotation.JSONField;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "ons")
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

}
