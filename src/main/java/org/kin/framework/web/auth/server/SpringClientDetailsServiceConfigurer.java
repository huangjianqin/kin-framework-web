package org.kin.framework.web.auth.server;

import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;

/**
 * @author huangjianqin
 * @date 2021/8/1
 */
@FunctionalInterface
public interface SpringClientDetailsServiceConfigurer {
    /**
     * 配置client details service
     */
    void configure(ClientDetailsServiceConfigurer clients);
}
