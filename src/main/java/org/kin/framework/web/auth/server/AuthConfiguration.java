package org.kin.framework.web.auth.server;

import org.kin.framework.web.auth.server.controller.AuthController;
import org.kin.framework.web.auth.server.controller.KeyPairController;
import org.springframework.context.annotation.Bean;

/**
 * @author huangjianqin
 * @date 2021/7/29
 */
public class AuthConfiguration {
    @Bean
    public OAuth2ExceptionHandler oauth2ExceptionHandler() {
        return new OAuth2ExceptionHandler();
    }

    @Bean
    public UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public AuthController authController() {
        return new AuthController();
    }

    @Bean
    public KeyPairController keyPairController() {
        return new KeyPairController();
    }

    @Bean
    public JwtTokenEnhancer jwtTokenEnhancer() {
        return new JwtTokenEnhancer();
    }
}
