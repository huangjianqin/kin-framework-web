package org.kin.framework.web.auth.server;

import org.kin.framework.web.domain.WebResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理Oauth2异常
 *
 * @author huangjianqin
 * @date 2021/7/29
 */
@ControllerAdvice
public class OAuth2ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public WebResponse<String> handleOauth2Ext(OAuth2Exception e) {
        return WebResponse.fail(e.getMessage());
    }
}
