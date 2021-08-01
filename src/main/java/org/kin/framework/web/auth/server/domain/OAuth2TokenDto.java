package org.kin.framework.web.auth.server.domain;

/**
 * @author huangjianqin
 * @date 2021/7/29
 */
public class OAuth2TokenDto {
    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷令牌
     */
    private String refreshToken;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;
    /**
     * 有效时间（秒）
     */
    private int expiresIn;

    public OAuth2TokenDto token(String token) {
        this.token = token;
        return this;
    }

    public OAuth2TokenDto refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public OAuth2TokenDto tokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
        return this;
    }

    public OAuth2TokenDto expiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    //setter && getter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
