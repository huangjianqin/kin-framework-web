package org.kin.framework.web.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限限制注解
 *
 * @author huangjianqin
 * @date 2019/7/26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permission {
    /**
     * @return 是否需要登录才可操作
     */
    boolean login() default true;

    /**
     * @return 是否需要管理员权限才可操作
     */
    boolean admin() default false;
}
