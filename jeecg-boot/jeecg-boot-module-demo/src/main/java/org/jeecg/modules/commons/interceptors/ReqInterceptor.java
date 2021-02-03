package org.jeecg.modules.commons.interceptors;


import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Log4j2
public class ReqInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<Long> contextHolder = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //拦截器的请求处理跨域的的问题
        contextHolder.set(System.currentTimeMillis());
//        response.addHeader("Access-Control-Allow-Origin", "*");
        log.info(new StringBuilder().append("请求信息### - IP:").append(request.getRemoteAddr())
                .append(" <<<<<请求地址>>>>>:").append(request.getRequestURI()).append(" - 请求参数：")
                .append(JSON.toJSONString(request.getParameterMap())));

        checkSign(request);
        checkSession(request);
        return true;
    }


    private boolean checkSession(HttpServletRequest request) {


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
