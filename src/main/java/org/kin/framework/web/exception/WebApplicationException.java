package org.kin.framework.web.exception;

/**
 * web异常统一处理
 *
 * @author huangjianqin
 * @date 2020/10/14
 */
public class WebApplicationException extends RuntimeException {
    private WebRespMessage webRespMessage;

    public WebApplicationException(WebRespMessage webRespMessage) {
        super(webRespMessage.message());
        this.webRespMessage = webRespMessage;
    }

    public WebApplicationException(String message) {
        super(message);
    }

    public WebApplicationException(Throwable cause) {
        super(cause);
    }

    public WebApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    //getter
    public WebRespMessage getWebRespMessage() {
        return webRespMessage;
    }
}
