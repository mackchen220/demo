package org.jeecg.common.exception;

public class JeecgBootException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	private Integer code;
	private String message;

	public JeecgBootException(String message){
		super(message);
	}

	public JeecgBootException(Integer code,String message){
		super();
		this.code=code;
		this.message=message;
	}

	public JeecgBootException(Throwable cause)
	{
		super(cause);
	}

	public JeecgBootException(String message,Throwable cause)
	{
		super(message,cause);
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return message;
	}

	public void setMsg(String msg) {
		this.message = msg;
	}
}
