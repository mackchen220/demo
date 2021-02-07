package org.jeecg.modules.commons.util;

import org.jeecg.common.exception.JeecgBootException;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidateTool {



    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 是否为空白字符串 字符串仅为空格、回车、换行、制表符、空字符串...时 返回false
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * 验证参数是否为空
     *
     * @param obj
     * @return 不为�?:true,为空false
     * @throws
     */
    public static boolean checkIsNull(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (((String) obj).trim().equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNull(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            if (((String) obj).trim().equals("")) {
                return true;
            }
        }
        return false;
    }


    public static boolean isNotNull(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (((String) obj).trim().equals("")) {
                return false;
            }
        }
        return true;
    }






//    String regName = "^[A-Za-z0-9]{6,40}$";
//        if (!userCustomerModel.getUsername().matches(regName)) {
//        throw new JeecgBootException(MessageUtil.getMessage("COMMON_CHEECKNAME_ERROR_CODE"), MessageUtil.getMessage("COMMON_CHEECKNAME_ERROR"));
//    }

    public static boolean checkIsWX(String str){
        //微信号码
//        微信号规则：微信账号仅支持6-20个字母、数字、下划线或减号，以字母开头。解释一下，只要是字母开头，可以是纯字母（hjqzHJQZhongjiqiezi），或字母数字混合
        String pattern = "^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}+$";
        boolean isMatch = Pattern.matches(pattern, str);
        if (isMatch==false) {
            throw new JeecgBootException("微信号不合法");
        }
        return true;
    };

    public static boolean checkIsQQ(String str){
        //数字范围5-14个之间
        String pattern = "[1-9][0-9]{4,14}";
        boolean isMatch = Pattern.matches(pattern, str);
        if (isMatch==false) {
            throw new JeecgBootException("QQ号不合法");
        }
        return true;
    }

//
//^[a-zA-Z][a-zA-Z0-9_]{4,15}$
    public static boolean checkIsName(String str){
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            return true;
        } else {
            throw new JeecgBootException("用户名不合法");
        }
    }
    /**
     * 验证参数长度
     *
     * @param code  参数名称
     * @param obj
     * @param begin
     * @param end
     * @return boolean
     * @throws
     */
    public static boolean checkParamLength(String code, Object obj, int begin, int end) {
        if (!checkIsNull(obj)) {
            throw new JeecgBootException(format("{0}不能为空，请确认", code));
        }
        if (obj instanceof String) {
            if (!checkLength((String) obj, begin, end)) {
                throw new JeecgBootException(format("验证码错误", code));
            }
        }
        return true;
    }

    /**
     * @description  验证参数是否为空以及参数长度是否符合要求
     * @param  code, obj, begin, end
     * @return boolean
     * @date 2019/6/24/024
     */
    public static boolean checkParam(String code, Object obj, int begin, int end) {
        if (!checkIsNull(obj)) {
            throw new JeecgBootException(format("{0}不能为空，请确认", code));
        }
        if (obj instanceof String) {
            if (!checkLength((String) obj, begin, end)) {
                throw new JeecgBootException(format("{0}长度不符合格式", code));
            }
        }

        return true;
    }

    public static String format(String message, Object... arg) {
        MessageFormat formatter = new MessageFormat(message);
        String output = formatter.format(arg);
        return output;
    }

    public static int totalLength(String content) {
        String regex = "[\u4e00-\u9fa5]";
        int length = content.length();
        int chineseLength =  content.length() - content.replaceAll(regex, "").length();
        return length+chineseLength;
    }


    /**
     * @description  判断字符串是不是汉字
     * @param: con
     * @param: ignore 是否忽略  ·  这个符号
     * @return boolean
     * @date 2019/9/20/020
     */
    public static boolean isChinese(String con,boolean ignore) {

        for (int i = 0; i < con.length(); i = i + 1) {
            if (ignore){
                if (!Pattern.compile("[\u4e00-\u9fa5]").matcher(
                        String.valueOf(con.charAt(i))).find() && !"·".equals(String.valueOf(con.charAt(i)))) {
                    return false;
                }
            }else {
                if (!Pattern.compile("[\u4e00-\u9fa5]").matcher(
                        String.valueOf(con.charAt(i))).find()) {
                    return false;
                }
            }

        }

        return true;
    }

    /**
     * @description 判断是不是中文或英文字母
     * @param: con
     * @return boolean
     * @date 2019/9/20/020
     */
    public static boolean conValidate(String con) {
        if (null != con && !"".equals(con)) {
            if ((isChinese(con,true) || con.matches("^[A-Za-z]+$"))) {
                return true;
            }
        }
        return false;
    }



    /**
     * 验证参数长度
     *
     * @param code 参数名称
     * @param obj
     * @return boolean
     * @throws
     */
    public static boolean checkParamIsNull(String code, Object obj) {
        if (!checkIsNull(obj)) {
            throw new JeecgBootException(format("{0}不能为空，请确认", code));
        }
        return true;
    }



    public static boolean checkLength(String str, int begin, int end) {
        if (str == null) {
            return false;
        }
        if (str.length() <= end && str.length() >= begin) {
            return true;
        }
        return false;
    }

    /**
     * 验证是否为手机号�?
     *
     * @throws JeecgBootException
     */
    public static boolean checkParamIsMobile(String mobile) {
        if (mobile == null || mobile == "") {
            throw new JeecgBootException( "手机号不能为空");
        }
        if (!isMobileNO(mobile)) {
            throw new JeecgBootException( "手机号错误");
        }
        return true;
    }

    //验证手机号码格式
    public static boolean isMobileNO(String mobiles) {
//		if (mobiles == null ) {
//			return false;
//		}
//		else {
//			return true;
//		}
        if (!checkIsNull(mobiles)) {
            return false;
        }
        String pattern = "(0|86)?(13[0-9]|14[0-9]|15[0-9]|16[012345689]|17[2-9]|18[0-9]|19[0-9])[0-9]{8}";
        //手机号码
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mobiles);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 验证输入的邮箱格式是否符合
     *
     * @param email
     * @return 是否合法
     */
    public static boolean emailFormat(String email) {
        boolean tag = true;
        final String pattern1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        final Pattern pattern = Pattern.compile(pattern1);
        final Matcher mat = pattern.matcher(email);
        if (!mat.find()) {
            tag = false;
        }
        return tag;
    }


    public static boolean isNum(String code, String value) {
        if (value == null) {
            throw new JeecgBootException(format("{0}不能为空，请确认", code));
        }
        String pattern = "^[0-9]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        if (!m.matches()) {
            throw new JeecgBootException(format("{0}数据不符合格式", code));
        }
        return true;
    }

    public static boolean isDecimalValid(String code, String str, int start, int end) {
        checkParamIsNull(code, str);
        int s = 0;
        try {
            s = Integer.parseInt(str);
        } catch (Exception e) {
            throw new JeecgBootException(format("{0}数据不符合格式", code));
        }

        if (end == start) {
            if (end == s) {
                return true;
            } else {
                throw new JeecgBootException(format("{0}数据不符合格式", code));
            }
        } else {
            if (s <= end && s >= start) {
                return true;
            }
        }
        throw new JeecgBootException(format("{0}数据不符合格式", code));
    }


}
