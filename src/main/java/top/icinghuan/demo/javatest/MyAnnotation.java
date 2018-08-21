package top.icinghuan.demo.javatest;

import java.lang.annotation.*;

/**
 * @author : xy
 * @date : 2018/7/25
 * Description :
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String name() default "Hello";
}
