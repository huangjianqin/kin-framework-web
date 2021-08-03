/**
 * @author huangjianqin
 * @date 2021/7/26
 */
package org.kin.framework.web.auth.server;

/**
 * oauth server工具类
 * <p>
 * 1. 实现{@link org.kin.framework.web.auth.server.UserSupplier} 定义User信息加载逻辑
 * 2. 实现{@link org.kin.framework.web.auth.server.SpringClientDetailsServiceConfigurer}, 自定义client details service配置
 * 3. (可选)实现{@link org.kin.framework.web.auth.server.HttpSecurityConfigurer} 自定义http security配置
 * 4. 使用注解{@link org.kin.framework.web.auth.server.EnableOAuth2Server}
 */