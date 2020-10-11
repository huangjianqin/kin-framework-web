package org.kin.framework.web.log;

import java.util.List;

/**
 * 接口访问日志
 *
 * @author huangjianqin
 * @date 2020/10/11
 */
public class WebAccessLog {
    /**
     * 操作描述
     */
    private String description;

    /**
     * 操作用户
     */
    private String username;

    /**
     * 操作时间
     */
    private long startTime;

    /**
     * 消耗时间
     */
    private long costTime;

    /**
     * 根路径
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * 请求类型, get post等等
     */
    private String method;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 请求参数
     */
    private List<Object> params;

    /**
     * 返回结果
     */
    private Object result;

    public static WebAccessLog of(String description, String username, long startTime, long costTime, String basePath, String uri, String url, String method, String ip, List<Object> params, Object result) {
        WebAccessLog inst = new WebAccessLog();
        inst.description = description;
        inst.username = username;
        inst.startTime = startTime;
        inst.costTime = costTime;
        inst.basePath = basePath;
        inst.uri = uri;
        inst.url = url;
        inst.method = method;
        inst.ip = ip;
        inst.params = params;
        inst.result = result;
        return inst;
    }

    //setter && getter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getCostTime() {
        return costTime;
    }

    public void setCostTime(long costTime) {
        this.costTime = costTime;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
