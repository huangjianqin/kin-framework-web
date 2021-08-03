package org.kin.framework.web.auth.doamin;

/**
 * 校验相关的消息提示
 *
 * @author huangjianqin
 * @date 2021/7/29
 */
public interface AuthMessages {
    String USERNAME_PASSWORD_ERROR = "用户名或密码错误!";

    String CREDENTIALS_EXPIRED = "该账户的登录凭证已过期，请重新登录!";

    String ACCOUNT_DISABLED = "该账户已被禁用，请联系管理员!";

    String ACCOUNT_LOCKED = "该账号已被锁定，请联系管理员!";

    String ACCOUNT_EXPIRED = "该账号已过期，请联系管理员!";

    String PERMISSION_DENIED = "没有访问权限，请联系管理员!";
}
