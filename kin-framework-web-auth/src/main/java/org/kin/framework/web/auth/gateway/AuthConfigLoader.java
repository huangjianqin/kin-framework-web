package org.kin.framework.web.auth.gateway;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Collections;

/**
 * 设置默认的oauth2 resource server配置
 *
 * @author huangjianqin
 * @date 2021/8/3
 */
public class AuthConfigLoader implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment environment = event.getEnvironment();
        //应用名
        String appName = environment.getProperty("spring.application.name", "mock");
        //默认auth是9000
        int port = environment.getProperty("server.port", Integer.class, 9000);

        //默认的请求public key接口, 也允许使用者自己定义
        String jwkSetUri = "http://0.0.0.0:" + port + "/" + appName + "/rsa/publicKey";
        environment.getPropertySources().addLast(
                new MapPropertySource("kin-auth",
                        Collections.singletonMap("spring.security.oauth2.resourceserver.jwt.jwk-set-uri", jwkSetUri)));
    }
}
