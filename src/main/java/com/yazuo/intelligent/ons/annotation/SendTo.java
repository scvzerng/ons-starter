package com.yazuo.intelligent.ons.annotation;

import com.yazuo.intelligent.ons.enums.ReturnType;

import java.lang.annotation.*;

/**
 * description
 * <p>
 * 2017-11-30 10:31
 *
 * @author scvzerng
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SendTo {

    /**
     * 您在控制台创建的Producer ID
     */
    String id();
    /**
     * 设置发送超时时间，单位毫秒
     */
    String sendMsgTimeoutMillis() default "3000";

    /**
     * 发送目标topic
     * @return
     */
    String topic();

    String tag() default "DEFAULT";

    ReturnType returnType() default ReturnType.VOID;
}
