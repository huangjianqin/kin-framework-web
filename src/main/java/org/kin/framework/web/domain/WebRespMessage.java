package org.kin.framework.web.domain;

/**
 * response 响应码+描述
 *
 * @author huangjianqin
 * @date 2020/10/14
 */
public interface WebRespMessage {
    /**
     * @return 响应码
     */
    int code();

    /**
     * @return 描述
     */
    String message();
}
