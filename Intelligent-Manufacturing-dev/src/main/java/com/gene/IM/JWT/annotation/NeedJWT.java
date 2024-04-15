package com.gene.IM.JWT.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)  // 表明此注解作用在什么对象上面（类、属性、方法）
@Retention(RetentionPolicy.RUNTIME)  // 标记此注解在何时被检查到（运行时、编译时、、）
@Documented
public @interface NeedJWT {
    boolean required() default true;
}
