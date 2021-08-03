package org.kin.framework.web.auth.server.controller;

import org.kin.framework.web.auth.doamin.AuthConstants;
import org.kin.framework.web.auth.doamin.AuthMessages;
import org.kin.framework.web.auth.server.domain.OAuth2TokenDto;
import org.kin.framework.web.domain.WebResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;
import java.util.Objects;

/**
 * 自定义Oauth2获取令牌接口
 *
 * @author huangjianqin
 * @date 2021/7/29
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {
    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
     * 认证中心登录认证
     * grant_type: 授权模式
     * client_id: Oauth2客户端ID
     * client_secret: Oauth2客户端秘钥
     * refresh_token: 刷新token
     * username: 登录用户名
     * password: 登录密码
     */
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public WebResponse<OAuth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters)
            throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        if (Objects.isNull(oAuth2AccessToken)) {
            return WebResponse.fail(AuthMessages.USERNAME_PASSWORD_ERROR);
        }

        OAuth2TokenDto oAuth2TokenDto = new OAuth2TokenDto()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                //目前只支持jwt
                .tokenHead(AuthConstants.JWT_TOKEN_PREFIX);

        return WebResponse.success(oAuth2TokenDto);
    }
}
