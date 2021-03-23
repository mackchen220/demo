package org.jeecg.modules.commons.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
public class HttpUtil {

    private HttpUtil() {
        //工具类不能实例化
    }

    public static String httpPost(String httpUrl, Map<String, Object> paramMap) {
        log.info("======远程调用url:" + httpUrl + ",调用参数======>" + JSON.toJSONString(paramMap));
        String res = cn.hutool.http.HttpUtil.post(httpUrl, paramMap, 20000);
        log.info("======远程调用返回值======" + JSON.toJSONString(res));
        return res;
    }

    public static String httpPost(String httpUrl, Map<String, Object> paramMap, int timeout) {
        log.info("======远程调用url:" + httpUrl + ",调用参数======>" + JSON.toJSONString(paramMap));
        String res = cn.hutool.http.HttpUtil.post(httpUrl, paramMap, timeout);
        log.info("======远程调用返回值======" + JSON.toJSONString(res));
        return res;
    }

    public static String httpGet(String httpUrl, Map<String, Object> paramMap) {
        log.info("======远程调用url:" + httpUrl + ",调用参数======>" + JSON.toJSONString(paramMap));
        String res = cn.hutool.http.HttpUtil.get(httpUrl, paramMap, 20000);
        log.info("======远程调用返回值======" + JSON.toJSONString(res));
        return res;
    }

    public static String httpGet(String httpUrl, Map<String, Object> paramMap, int timeout) {
        log.info("======远程调用url:" + httpUrl + ",调用参数======>" + JSON.toJSONString(paramMap));
        String res = cn.hutool.http.HttpUtil.get(httpUrl, paramMap, timeout);
        log.info("======远程调用返回值======" + JSON.toJSONString(res));
        return res;
    }

}
