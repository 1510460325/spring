package cn.wzy.annotation;

import java.lang.annotation.*;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/2 14:19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface WzyAutowired {

	String value() default "";

}
