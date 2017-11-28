package com.yazuo.intelligent.ons.config;

import lombok.Data;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.util.Properties;

import static com.aliyun.openservices.ons.api.PropertyKeyConst.AccessKey;
import static com.aliyun.openservices.ons.api.PropertyKeyConst.SecretKey;
@Data
public abstract class AbstractConfig implements ExportOnsConfig{
    protected Environment environment;
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


    @Override
    public Properties export() {
        Properties properties = new Properties();
        properties.put(AccessKey,environment.resolveRequiredPlaceholders(accessKey));
        properties.put(SecretKey,environment.resolveRequiredPlaceholders(secretKey));
        if(!StringUtils.isEmpty(address)){
            properties.put(AccessKey,environment.resolveRequiredPlaceholders(accessKey));
        }
        return properties;
    }
}
