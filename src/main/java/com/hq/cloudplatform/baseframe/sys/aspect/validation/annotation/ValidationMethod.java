package com.hq.cloudplatform.baseframe.sys.aspect.validation.annotation;

import java.lang.annotation.*;

/**
 * 用于标识需要对方法的参数实体进行校验的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ValidationMethod {

    /**
     * 标识是否为”修改“类的方法，方法类的方法不需要进行非空校验
     * 只需要满足正则表达式的要求即可
     *
     * @return
     */
    boolean isUpdate() default false;
}
