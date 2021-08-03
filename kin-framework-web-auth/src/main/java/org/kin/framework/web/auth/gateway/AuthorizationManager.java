package org.kin.framework.web.auth.gateway;

import com.nimbusds.jose.JWSObject;
import org.kin.framework.utils.JSON;
import org.kin.framework.utils.StringUtils;
import org.kin.framework.web.auth.doamin.AuthConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 *
 * @author huangjianqin
 * @date 2021/8/1
 */
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private static final Logger log = LoggerFactory.getLogger(AuthorizationManager.class);

    @Autowired
    private WhiteListProperties whiteListProperties;
    @Autowired
    private AuthRolesSupplier authRolesSupplier;

    @SuppressWarnings("unchecked")
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();

        //白名单直接放行
        List<String> whiteList = whiteListProperties.getUrls();
        for (String url : whiteList) {
            if (pathMatcher.match(url, uri.getPath())) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }

        //对应跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        //不同用户体系登录不允许互相访问
        try {
            String token = request.getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
            if (StringUtils.isBlank(token)) {
                //token 为空
                return Mono.just(new AuthorizationDecision(false));
            }
            String realToken = token.replace(AuthConstants.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            String userJson = jwsObject.getPayload().toString();
            //json内容
            Map<String, Object> map = JSON.read(userJson, Map.class);

            Object idObj = map.get("id");
            long id = Objects.nonNull(idObj) ? (long) idObj : 0L;
            Object clientIdObj = map.get("clientId");
            String clientId = Objects.nonNull(clientIdObj) ? clientIdObj.toString() : "";

            List<String> roles = authRolesSupplier.getRoles(id, clientId, uri.getPath());
            List<String> rolesWithPrefix = roles.stream().map(r -> AuthConstants.AUTHORITY_PREFIX + r).collect(Collectors.toList());

            //认证通过且角色匹配的用户可访问当前路径
            return mono
                    .filter(Authentication::isAuthenticated)
                    .flatMapIterable(Authentication::getAuthorities)
                    .map(GrantedAuthority::getAuthority)
                    .any(rolesWithPrefix::contains)
                    .map(AuthorizationDecision::new)
                    .defaultIfEmpty(new AuthorizationDecision(false));
        } catch (ParseException e) {
            log.error("", e);
            return Mono.just(new AuthorizationDecision(false));
        }
    }

}
