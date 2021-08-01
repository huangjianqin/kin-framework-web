package org.kin.framework.web.auth.server;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author huangjianqin
 * @date 2021/8/1
 */
@FunctionalInterface
public interface HttpSecurityConfigurer {
    /**
     * 配置http security
     */
    void configure(HttpSecurity http);
}
