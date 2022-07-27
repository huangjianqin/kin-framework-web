package org.kin.framework.web.auth.server;

import org.kin.framework.spring.condition.ConditionOnMissingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * @author huangjianqin
 * @date 2021/7/29
 */
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired(required = false)
    private HttpSecurityConfigurer httpSecurityConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //默认不校验actuator
                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                //默认不校验请求公钥接口
                .antMatchers("/rsa/publicKey").permitAll()
                .anyRequest().authenticated();
        if (Objects.nonNull(httpSecurityConfigurer)) {
            httpSecurityConfigurer.configure(http);
        }
    }

    @Bean
    @ConditionOnMissingBean(AuthenticationManager.class)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @ConditionOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

