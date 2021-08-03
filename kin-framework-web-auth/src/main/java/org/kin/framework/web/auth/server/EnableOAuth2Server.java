package org.kin.framework.web.auth.server;

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
@Import({OAuth2ServerConfiguration.class, AuthConfiguration.class, WebSecurityConfiguration.class})
public @interface EnableOAuth2Server {
}
