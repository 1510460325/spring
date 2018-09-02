package cn.wzy.annotation;

import java.lang.annotation.*;

/**
 * @author wzy 不短不长八字刚好.
 * @since 2018/9/2 15:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface WzyRequestParam {

	String value() default "";

}
