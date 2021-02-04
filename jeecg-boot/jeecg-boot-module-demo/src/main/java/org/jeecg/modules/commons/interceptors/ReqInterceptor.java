package org.jeecg.modules.commons.interceptors;


import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.commons.RedisKey;
import org.jeecg.modules.commons.util.ValidateTool;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        log.info(new StringBuilder().append("请求信息### - IP:").append(request.getRemoteAddr())
                .append(" <<<<<请求地址>>>>>:").append(request.getRequestURI()).append(" - 请求参数：")
                .append(JSON.toJSONString(request.getParameterMap())));

        checkSession(request);
        checkSession(request);
        return true;
    }


    private boolean checkSession(HttpServletRequest request) {
        String[] unAuthList = {"/hy/user/userLogin", "hy/index/getCaptchaCode","/sys","/online","/mock","/jmreport"
                ,"/bigscreen","/test/bigScreen","/swagger","/webjars","/druid","/generic"};
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


        String token = request.getParameter("token");
        if (token == null) {
            token = request.getHeader("X-Access-Token");
        }

        if (ValidateTool.checkIsNull(token)) {
            String Secret;
            String[] split = token.split(",");
            try {
                Secret = (String) redisUtil.get(RedisKey.USER_LOGIN_TOKEN + RedisKey.KEY_SPLIT + split[0]);
            } catch (JeecgBootException e) {
                log.warn("token错误{}", token);
                throw new JeecgBootException("登录信息过期，请重新登录");
            }
            if (ValidateTool.checkIsNull(Secret)) {
                if (!Secret.equals(split[1])) {
                    throw new JeecgBootException("账号在异地登录，请重新登录");
                }
            } else {
                throw new JeecgBootException("登录信息过期，请重新登录");
            }
        } else {
            throw new JeecgBootException("登录信息过期，请重新登录");
        }

        return true;
    }


    private boolean checkSign(HttpServletRequest request) {

        //接口验签暂时不做
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
