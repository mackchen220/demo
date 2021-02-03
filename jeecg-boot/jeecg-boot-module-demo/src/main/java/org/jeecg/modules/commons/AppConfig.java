package org.jeecg.modules.commons;

import org.jeecg.modules.commons.interceptors.ReqInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class AppConfig implements WebMvcConfigurer {

    @org.springframework.context.annotation.Bean
    ReqInterceptor requestInterceptor() {
        return new ReqInterceptor();
    }

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/resources/**", "/img/**", "/images/**", "/AppImage/**", "/GoodImage/**");
    }


}
