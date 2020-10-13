package org.kin.framework.web.domain;

import org.kin.framework.web.exception.CommonWebRespMessage;
import org.kin.framework.web.exception.WebRespMessage;

import java.io.Serializable;

/**
 * web响应封装
 *
 * @author huangjianqin
 * @date 2020-03-07
 */
public class WebResponse<T> implements Serializable {
    public static final long serialVersionUID = 42L;

    /** 状态码 */
    private int code;
    /** 消息提示 */
    private String msg;
    /** 响应内容 */
    private T content;

    public static <T> WebResponse<T> of(int code, String msg, T content) {
        WebResponse webResponse = new WebResponse<>();
        webResponse.code = code;
        webResponse.msg = msg;
        webResponse.content = content;
        return webResponse;
    }

    public static <T> WebResponse<T> of(WebRespMessage respMessage, T content) {
        WebResponse webResponse = new WebResponse<>();
        webResponse.code = respMessage.code();
        webResponse.msg = respMessage.message();
        webResponse.content = content;
        return webResponse;
    }

    public static <T> WebResponse<T> success(String msg, T content) {
        return WebResponse.of(CommonWebRespMessage.SUCCESS.code(), msg, content);
    }

    public static <T> WebResponse<T> success(T content) {
        return success("", content);
    }

    public static <T> WebResponse<T> success() {
        return success("", null);
    }

    public static <T> WebResponse<T> fail(int code, String msg) {
        return WebResponse.of(code, msg, null);
    }

    public static <T> WebResponse<T> fail(String msg) {
        return WebResponse.of(CommonWebRespMessage.FAILED.code(), msg, null);
    }

    public static <T> WebResponse<T> fail(WebRespMessage webRespMessage) {
        return WebResponse.of(webRespMessage, null);
    }

    //------------------------------------------------------------------------------------------------------

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WebResult [code=" + code + ", msg=" + msg + ", content=" + content + "]";
    }

}