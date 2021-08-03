package org.kin.framework.web.domain;

/**
 * 列出一些常用(响应码+描述)
 *
 * @author huangjianqin
 * @date 2020/10/14
 */
public enum CommonWebRespMessage implements WebRespMessage {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限"),
    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(404, "参数检验失败"),
    /**
     * 操作失败
     */
    FAILED(500, "操作失败");

    /** 响应码 */
    private int code;
    /** 描述 */
    private String message;

    private CommonWebRespMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}

