package com.jthinking.deploy.web.exception;

/**
 * 自定义异常类
 * @author JiaBochao
 * @version 2017-11-22 10:05:29
 */
public class UserException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserException(String message) {
		super(message);
	}

}
