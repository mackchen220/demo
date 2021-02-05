package org.jeecg.modules.commons;

/**
 * 异常错误号
 * 1000 ~ 1999 系统级异常
 * 2000 ~ ∞ 业务级异常
 * @author momo
 *
 */
public enum ErrorInfoCode {

	/** 参数校验失败 */
	PARAMS_ERROR (1001, "参数异常"),
	/** md5比对校验失败 */
	VERIFY_FAIL (1002, "md5校验失败"),
	/** 请求超时 */
	TIME_OUT (1003, "请求处理超时"),
	/** 签名校验失败 */
	SIGN_ERROR(26000, "签名校验失败"),
	/** http调用失败 */
	HTTP_EXECUTE_ERROR (1005,"http调用错误，execute error"),
	/** http调用异常 */
	HTTP_EC_ERROR (1007, "http调用成功，但返回错误数据,错误号为{1}"),
	/** 权限异常 */
	NO_LOGIN_ERROR(40002, "未登录"),
	/** 服务器内部异常 */
	UN_KNOW_ERROR(99999, "网络异常，请稍后重试"),

	;

	private int code;

	private String msg;

	ErrorInfoCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static ErrorInfoCode getErrorByCode(int code) {
		for(ErrorInfoCode errorInfoCode : ErrorInfoCode.values()){
			if(errorInfoCode.getCode() == code){
				return errorInfoCode;
			}
		}
		return UN_KNOW_ERROR;
	}

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

}
