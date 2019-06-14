package com.zheng.utils.handle;

/**
 * 功能描述： 似乎直接使用new Exception会过于消耗性能，所以使用这种形式
 * 
 */
public class MyException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 *
	 * 
	 * @param msg 错误输出信息
	 */
	public MyException(String msg) {
		this(msg, true);
	}

	/**
	 * 
	 * @param msg              自定义信息
	 * @param recordStackTrace 是否记录异常，设置为false时性能最高
	 */
	public MyException(String msg, boolean recordStackTrace) {
		super(msg, null, false, recordStackTrace);
	}

	/**
	 * 
	 * @param msg   自定义错误信息
	 * @param cause 记录栈异常
	 */
	public MyException(String msg, Throwable cause) {
		super(msg, cause, false, true);
	}
}
