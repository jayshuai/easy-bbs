package org.example.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface GlobalInterceptor {
    /**
     * 是否需要登录
     */
    boolean checkLogin() default false;

    /**
     * 是否需要检验参数
     * @return
     */
    boolean checkParams() default false;

    /**
     * 检验频次
     */

}
