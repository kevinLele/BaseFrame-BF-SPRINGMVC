package com.hq.cloudplatform.baseframe.sys.aspect.dictionary.annotation;

import java.lang.annotation.*;

/**
 * 将类的属性标识为数据字典字段, 被标识为数据字典的字段会被切面转换成数据字典的名称
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface DictionaryField {

    /**
     * 父数据字典的Code值
     *
     * @return
     */
    String value() default "";

    /**
     * 父数据字典的Code的值是否是动态的
     * 例如：在页面中有资源大类和资源子类的两个选项，资源子类中的选项是根据资源大类所选中的选项才变化的
     * 所以针对这种情况时没法预知资源子类所对应的父数据字典的确切值，此时需要将该属性标识为动态的父数据字典并设置父数据字典来源于哪个字段
     * 配置样例如下：
     *      @DictionaryField(isDynamicParentCode = true, dynamicCodeField = "serviceTypeParentCode")
     *      private String serviceType;
     *
     *      private String serviceTypeParentCode; //该字段仅仅用于保存动态的父数据字典的CODE值，进行数据字典转换时需要使用
     *
     * @return
     */
    boolean isDynamicParentCode() default false;

    /**
     * 根据该属性所指定的字段名来动态获取父数据字典的Code值
     *
     * @return
     */
    String dynamicCodeField() default "";
}
