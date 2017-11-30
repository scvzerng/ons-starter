package com.yazuo.intelligent.ons.annotation;


import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Deprecated
/**
 * @see SendTo
 */
public @interface OnsProducer {

    /**
     * 您在控制台创建的Producer ID
     */
     String id() ;
    /**
     * 设置发送超时时间，单位毫秒
     */
     String sendMsgTimeoutMillis() default "3000";

}
