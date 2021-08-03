package org.kin.framework.web.auth.gateway;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 白名单配置
 *
 * @author huangjianqin
 * @date 2021/8/1
 */
@ConfigurationProperties(prefix = "kin.web.whitelist")
public class WhiteListProperties {
    private List<String> urls;

    //setter && getter
    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
