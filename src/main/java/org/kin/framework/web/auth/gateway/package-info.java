/**
 * @author huangjianqin
 * @date 2021/8/1
 */
package org.kin.framework.web.auth.gateway;

/**
 * gateway 认证工具类
 * <p>
 * 1. 实现{@link org.kin.framework.web.auth.gateway.AuthRolesSupplier}, 自定义获取用户角色信息
 * 2. 使用注解{@link org.kin.framework.web.auth.gateway.EnableGatewayAuth}
 * 3. (可选)配置白名单, 参考{@link org.kin.framework.web.auth.gateway.WhiteListProperties}
 */