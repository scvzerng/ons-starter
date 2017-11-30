package com.yazuo.intelligent.ons.config;

import org.springframework.util.StringUtils;

import java.util.Properties;

import static com.aliyun.openservices.ons.api.PropertyKeyConst.AccessKey;
import static com.aliyun.openservices.ons.api.PropertyKeyConst.SecretKey;
public abstract class AbstractConfig extends Properties{
    /**
     * AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    protected String accessKey;
    /**
     * SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
     */
    protected String secretKey;

    /**
     *设置 TCP 接入域名
     */
    protected String address;


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.put(AccessKey,accessKey);

        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.put(SecretKey,secretKey);

        this.secretKey = secretKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(!StringUtils.isEmpty(address)){
            this.put(AccessKey,accessKey);
        }
        this.address = address;
    }

}
