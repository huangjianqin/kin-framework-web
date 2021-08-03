package org.kin.framework.web.log;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author huangjianqin
 * @date 2020/12/15
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(WebAccessLogAspect.class)
public @interface EnableAccessLog {
}
