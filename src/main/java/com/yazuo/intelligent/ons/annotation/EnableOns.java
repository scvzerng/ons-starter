package com.yazuo.intelligent.ons.annotation;

import com.yazuo.intelligent.ons.OnsListenerAnnotationBeanPostProcessor;
import com.yazuo.intelligent.ons.OnsProducerAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OnsProducerAnnotationBeanPostProcessor.class, OnsListenerAnnotationBeanPostProcessor.class})
public @interface EnableOns {
}
