package org.kin.framework.web.auth.gateway;

import java.util.List;

/**
 * @author huangjianqin
 * @date 2021/8/1
 */
@FunctionalInterface
public interface AuthRolesSupplier {
    /**
     * 获取指定url path下, 指定用户拥有的roles
     *
     * @param userId   用户唯一id
     * @param clientId client id
     * @param path     url path
     * @return 注意, 返回值的role描述不需要带ROLE_前缀
     */
    List<String> getRoles(long userId, String clientId, String path);
}
