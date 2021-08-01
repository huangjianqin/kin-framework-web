package org.kin.framework.web.auth.doamin;

/**
 * @author huangjianqin
 * @date 2021/7/29
 */
public interface AuthConstants {
    /**
     * JWT role前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-app";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";
}
