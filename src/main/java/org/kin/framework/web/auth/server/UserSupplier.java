package org.kin.framework.web.auth.server;

import org.kin.framework.web.auth.server.domain.BaseUserDetails;

/**
 * @author huangjianqin
 * @date 2021/7/29
 */
@FunctionalInterface
public interface UserSupplier {
    /**
     * 获取用户自定义数据
     *
     * @param clientId 客户端id
     * @param username 用户唯一标识
     */
    BaseUserDetails get(String clientId, String username);
}
