package org.jeecg.common.exception;

import org.jeecg.common.api.vo.ErrorInfoCode;

public class JeecgBootException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private int code;
	private String message;
	private ErrorInfoCode errorInfoCode;

	public JeecgBootException(ErrorInfoCode errorInfoCode) {
		this.errorInfoCode = errorInfoCode;
	}

	public JeecgBootException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public JeecgBootException(String message){
		super(message);
	}
	
	public JeecgBootException(Throwable cause)
	{
		super(cause);
	}
	
	public JeecgBootException(String message,Throwable cause)
	{
		super(message,cause);
	}

	public int getCode() {
		if (errorInfoCode != null) {
			return errorInfoCode.getCode();
		} else {
			return code;
		}
	}

	@Override
	public String getMessage() {
		if (errorInfoCode != null) {
			return errorInfoCode.getMsg();
		} else {
			return message;
		}
	}

}
