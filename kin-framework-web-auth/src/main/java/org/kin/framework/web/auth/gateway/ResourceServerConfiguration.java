package org.kin.framework.web.auth.gateway;

import org.kin.framework.web.auth.doamin.AuthConstants;
import org.kin.framework.web.auth.gateway.filter.AuthGlobalFilter;
import org.kin.framework.web.auth.gateway.filter.WhiteListFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 资源服务器配置
 *
 * @author huangjianqin
 * @date 2021/8/1
 */
@EnableWebFluxSecurity
@EnableConfigurationProperties(WhiteListProperties.class)
public class ResourceServerConfiguration {
    @Autowired
    private WhiteListProperties whiteListProperties;

    @Bean
    public AuthorizationManager authorizationManager() {
        return new AuthorizationManager();
    }

    @Bean
    public NoAuthAccessDeniedHandler noAuthAccessDeniedHandler() {
        return new NoAuthAccessDeniedHandler();
    }

    @Bean
    public TokenInvalidAuthenticationEntryPoint tokenInvalidAuthenticationEntryPoint() {
        return new TokenInvalidAuthenticationEntryPoint();
    }

    @Bean
    public AuthGlobalFilter authGlobalFilter() {
        return new AuthGlobalFilter();
    }

    @Bean
    public WhiteListFilter whiteListFilter() {
        return new WhiteListFilter();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstants.AUTHORITY_CLAIM_NAME);
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.oauth2ResourceServer().jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
        //自定义处理JWT请求头过期
        http.oauth2ResourceServer().authenticationEntryPoint(tokenInvalidAuthenticationEntryPoint());
        //白名单，直接移除JWT请求头
        http.addFilterBefore(whiteListFilter(), SecurityWebFiltersOrder.AUTHENTICATION);
        http.authorizeExchange()
                //配置白名单
                .pathMatchers(whiteListProperties.getUrls().toArray(new String[0])).permitAll()
                //配置鉴权管理器
                .anyExchange().access(authorizationManager())
                .and().exceptionHandling()
                //未授权处理
                .accessDeniedHandler(noAuthAccessDeniedHandler())
                //未认证处理
                .authenticationEntryPoint(tokenInvalidAuthenticationEntryPoint())
                .and().csrf().disable();
        return http.build();
    }
}
