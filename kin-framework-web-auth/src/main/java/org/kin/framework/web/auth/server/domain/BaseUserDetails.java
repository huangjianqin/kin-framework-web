package org.kin.framework.web.auth.server.domain;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author huangjianqin
 * @date 2021/7/29
 */
public abstract class BaseUserDetails implements UserDetails {
    /** 用户唯一id */
    protected long id;
    /** 登录客户端ID */
    protected String clientId;

    //setter && getter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }
}
