package com.jthinking.deploy.pojo;

/**
 * RESTFul接口调用返回的结果对象
 * 返回的数据原则上不能有与特定语言相关的标识符等。
 * Java最常见的是null，所有返回null的属性可以用空字符串代替。
 * @author JiaBochao
 * @version 2017-11-22 09:56:21
 */
public class SysResult {
    /**
     * 状态码。
     * 成功：200
     * 失败：500
     */
    private final Integer status;

    /**
     * 错误信息。
     * 失败：错误信息
     * 成功：空字符串
     */
    private final String error;

    /**
     * 数据。
     * 失败：空字符串
     * 成功：需要返回的数据，不需要返回数据时也是空字符串
     */
    private Object data;

    private SysResult(String error) {
        this.error = error;
        this.status = 500;
        this.data = "";
    }

    private SysResult() {
        this.error = "";
        this.status = 200;
        this.data = "";
    }

    public static SysResult success() {
        return new SysResult();
    }

    public static SysResult error(String error) {
        return new SysResult(error);
    }

    public SysResult data(Object data) {
        this.data = data;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }
}
