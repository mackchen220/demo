package org.jeecg.modules.commons.interceptors;


import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.IPUtils;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.Constant;
import org.jeecg.modules.commons.ErrorInfoCode;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.MapUtil;
import org.jeecg.modules.commons.util.ValidateTool;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Log4j2
public class ReqInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<Long> contextHolder = new ThreadLocal<>();

    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截器的请求处理跨域的的问题
        contextHolder.set(System.currentTimeMillis());
//        response.addHeader("Access-Control-Allow-Origin", "*");
        log.info(new StringBuilder().append("请求信息### - IP:").append(IPUtils.getIpAddr(request))
                .append(" <<<<<请求地址>>>>>:").append(request.getRequestURI()).append(" - 请求参数：")
                .append(JSON.toJSONString(request.getParameterMap())).append("token:").append(request.getHeader("token"))
                .append("<<<app版本号>>>:").append(request.getHeader("<<<version>>>")).append("后台程序版本119"));
        checkSession(request);
//        checkSign(request);
        return true;
    }


    private boolean checkSession(HttpServletRequest request) {
        String[] unAuthList = {"/hy/user/userLogin", "hy/index/getCaptchaCode","/sys","/online","/mock","/jmreport"
                ,"/bigscreen","/test/bigScreen","/swagger","/webjars","/druid","/generic","/doc.html","/favicon.ico",
                "hy/index/loadAppVersion","/vip/adminVip","getDictItems","/user/userAdmin","/course/adminCourse",
                "/sys/common/static","/search/adminHotSearch","/turnImage/adminTurnImage","/party/adminParty",
                "/user/adminUser","/user/weixinLogin","/hy/user/bindUserPhone","/community/adminCommunity",
                "/index/getPhoneCaptchaCode","/smsConfig/adminSmsConfig","/verified/adminVerifiedConfig",
                "/bank/adminBank","/platformConfig/platformConfig","/hospital/adminHospital","pay/wxNotify"};
        for (String tem : unAuthList) {
            if (request.getRequestURI().contains(tem)) {
                return true;
            }
        }

        String[] unAuthPathList = {"/images/", "/AppImage/", "/invoke/"};
        for (String s : unAuthPathList) {
            Pattern pattern = Pattern.compile(s);
            Matcher matcher = pattern.matcher(request.getRequestURI());
            if (matcher.find()) {
                return true;
            }
        }


        String token = request.getHeader("token");
        if (ValidateTool.isNull(token)) {
            token = request.getParameter("token");
        }
        if (ValidateTool.isNotNull(token)) {
            String Secret;
            String[] split = token.split(",");
            try {
                Secret = (String) redisUtil.get(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + split[0]);
            } catch (JeecgBootException e) {
                log.warn("token错误{}", token);
                throw new JeecgBootException(ErrorInfoCode.LOGIN__TOKEN_ERROR.getCode(),ErrorInfoCode.LOGIN__TOKEN_ERROR.getMsg());
            }
            if (ValidateTool.isNotNull(Secret)) {
//                if (!Secret.equals(split[1])) {
//                    throw new JeecgBootException(ErrorInfoCode.LOGIN_ERROR.getCode(),ErrorInfoCode.LOGIN_ERROR.getMsg());
//                }
            } else {
                log.warn("token格式错误");
                throw new JeecgBootException(ErrorInfoCode.LOGIN__TOKEN_ERROR.getCode(),ErrorInfoCode.LOGIN__TOKEN_ERROR.getMsg());
            }
        } else {
            log.warn("获取不到token");
            throw new JeecgBootException(ErrorInfoCode.LOGIN__TOKEN_ERROR.getCode(),ErrorInfoCode.LOGIN__TOKEN_ERROR.getMsg());
        }

        return true;
    }


    private boolean checkSign(HttpServletRequest request) {

        //TODO 接口验签暂时不做

        String[] unAuthList = {"/sys","/online","/mock","/jmreport"
                ,"/bigscreen","/test/bigScreen","/swagger","/webjars","/druid","/generic","/doc.html"};
        for (String tem : unAuthList){
            if (request.getRequestURI().contains(tem)){
                return true;
            }
        }
        String regex="/images/";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request.getRequestURI());
        if (matcher.find()){
            return true;
        }

        //取出请求的所有参数（除了sign）并封装成一个map
        String sign = request.getParameter("sign");
        if(!ValidateTool.checkIsNull(sign)){
            throw new JeecgBootException("验签失败");
        }
        Enumeration enu=request.getParameterNames();
        Map paramMap = Maps.newHashMap();
        while(enu.hasMoreElements()){
            String paraName=(String)enu.nextElement();
            if(!"sign".equals(paraName)){
                paramMap.put(paraName,request.getParameter(paraName));
            }
        }
        Map sendMap = MapUtil.sortAscLowerCase(paramMap);
        //遍历map中的值
        StringBuffer mapValue = new StringBuffer();
        for (Object value : sendMap.values()) {
            mapValue.append(value);
        }
        log.info("==拼接参数值===="+mapValue.toString());
        String mySign = SecureUtil.md5(SecureUtil.md5(mapValue.toString())+ Constant.SIGN_KEY);
        log.info("==mySign===="+mySign);
        log.info("==sign===="+sign);
        if(!sign.equals(mySign)){
            throw new JeecgBootException("验签失败");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = contextHolder.get();
        if (System.currentTimeMillis() - startTime >= 3000) {
            log.warn("afterCompletion请求超时，请求地址:" + request.getRequestURI() + "，处理时间:" + (System.currentTimeMillis() - startTime));
        }
        log.info("afterCompletion请求地址:" + request.getRequestURI());
    }
}
