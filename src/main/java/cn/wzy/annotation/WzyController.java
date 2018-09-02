package cn.wzy.annotation;

import java.lang.annotation.*;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/2 14:17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WzyController {

	String value() default "";

}
