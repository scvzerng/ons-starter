package com.yazuo.intelligent.ons.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.shade.com.alibaba.fastjson.annotation.JSONField;
import com.yazuo.intelligent.ons.annotation.OnsListener;
import com.yazuo.intelligent.ons.annotation.SendTo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.lang.annotation.Annotation;


@ConfigurationProperties(prefix = "ons")
@Data
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
     * 监听注解
     */
    private Class<? extends Annotation> listenerAnnotation = OnsListener.class;
    private Class<? extends Annotation> sendAnnotation = SendTo.class;



}
