package org.kin.framework.web.auth.gateway.filter;

import com.nimbusds.jose.JWSObject;
import org.kin.framework.utils.StringUtils;
import org.kin.framework.web.auth.doamin.AuthConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * 将登录用户的JWT token转化成用户信息的global filter
 *
 * @author huangjianqin
 * @date 2021/8/1
 */
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(AuthGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
        if (StringUtils.isBlank(token)) {
            return chain.filter(exchange);
        }
        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace(AuthConstants.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userJson = jwsObject.getPayload().toString();
            if (log.isDebugEnabled()) {
                log.debug("AuthGlobalFilter.filter() user:{}", userJson);
            }
            ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstants.USER_TOKEN_HEADER, userJson).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
