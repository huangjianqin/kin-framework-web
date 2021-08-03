package org.kin.framework.web.auth.gateway.filter;

import org.kin.framework.web.auth.doamin.AuthConstants;
import org.kin.framework.web.auth.gateway.WhiteListProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * 白名单路径访问时需要移除认证信息Http请求头
 *
 * @author huangjianqin
 * @date 2021/8/1
 */
public class WhiteListFilter implements WebFilter {
    @Autowired
    private WhiteListProperties whiteListProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径移除JWT请求头
        List<String> whiteList = whiteListProperties.getUrls();
        for (String url : whiteList) {
            if (pathMatcher.match(url, uri.getPath())) {
                request = exchange.getRequest().mutate().header(AuthConstants.JWT_TOKEN_HEADER, "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
