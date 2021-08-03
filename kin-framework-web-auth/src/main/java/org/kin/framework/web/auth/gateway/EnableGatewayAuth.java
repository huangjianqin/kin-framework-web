package org.kin.framework.web.auth.gateway;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author huangjianqin
 * @date 2021/8/1
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({GlobalCorsConfiguration.class, ResourceServerConfiguration.class})
public @interface EnableGatewayAuth {
}
