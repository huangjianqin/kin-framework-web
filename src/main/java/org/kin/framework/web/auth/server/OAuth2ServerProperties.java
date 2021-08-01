package org.kin.framework.web.auth.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author huangjianqin
 * @date 2021/8/1
 */
@ConfigurationProperties("org.kin.web")
public class OAuth2ServerProperties {
    /** jwt path */
    private String jwtPath;
    /** key store password */
    private String password;

    //setter && getter
    public String getJwtPath() {
        return jwtPath;
    }

    public void setJwtPath(String jwtPath) {
        this.jwtPath = jwtPath;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
