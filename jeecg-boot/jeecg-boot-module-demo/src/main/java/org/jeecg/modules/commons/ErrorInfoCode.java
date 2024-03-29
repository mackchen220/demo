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
	SIGN_ERROR(1004, "签名校验失败"),
	/** http调用失败 */
	HTTP_EXECUTE_ERROR (1005,"http调用错误，execute error"),
	/** http调用异常 */
	HTTP_EC_ERROR (1006, "http调用成功，但返回错误数据,错误号为{1}"),
	/** 权限异常 */
	NO_LOGIN_ERROR(1007, "未登录"),
	/** 服务器内部异常 */
	UN_KNOW_ERROR(1999, "网络异常，请稍后重试"),
	/** 查询类型不存在 */
	SEARCH_TYPE_ERROR(2001, "查询类型不存在"),
	UPLOAD_FILE_ERROR(2002, "文件上传失败"),
	UPLOAD_FILE_NOT_EXIST_ERROR(2003, "上传文件为空"),
	UPLOAD_FILE_OVER_LIMIT_ERROR(2004, "上次文件不能超过2MB"),
	/** 不满足接口条件 */
	NO_INVITE_CODE_ERROR(3001, "请填写邀请码"),
	LOGIN__TOKEN_ERROR(9998, "登录信息过期，请重新登录"),
	LOGIN_ERROR(9999, "账号在异地登录，请重新登录"),
	NUM_ERROR(9997, "来晚了，优惠名额不足"),


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
