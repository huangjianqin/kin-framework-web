package org.kin.framework.web.exception;

import org.kin.framework.web.domain.WebRespMessage;
import org.kin.framework.web.domain.WebResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author huangjianqin
 * @date 2020/10/14
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 应用异常通用处理
     */
    @ResponseBody
    @ExceptionHandler(value = WebApplicationException.class)
    public WebResponse<String> handle(WebApplicationException e) {
        WebRespMessage webRespMessage = e.getWebRespMessage();
        if (webRespMessage != null) {
            return WebResponse.fail(webRespMessage);
        }
        return WebResponse.fail(e.getMessage());
    }

    /**
     * controller方法参数校验异常通用处理
     */
    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public WebResponse<String> handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return WebResponse.validateFail(message);
    }

    /**
     * controller方法参数绑定异常通用处理
     */
    @ResponseBody
    @ExceptionHandler(value = BindException.class)
    public WebResponse<String> handle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return WebResponse.validateFail(message);
    }
}
