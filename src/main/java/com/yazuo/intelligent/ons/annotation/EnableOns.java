package com.yazuo.intelligent.ons.annotation;

import com.yazuo.intelligent.ons.parser.OnsListenerAnnotationBeanPostProcessor;
import com.yazuo.intelligent.ons.parser.OnsProducerAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({OnsProducerAnnotationBeanPostProcessor.class, OnsListenerAnnotationBeanPostProcessor.class})
public @interface EnableOns {
}
