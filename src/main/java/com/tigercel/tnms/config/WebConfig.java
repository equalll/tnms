package com.tigercel.tnms.config;

import com.tigercel.tnms.service.support.SViewHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by freedom on 2016/4/13.
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SViewHelper svh;

    @Autowired
    private I18NConfiguration i18NConfiguration;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(viewObjectAddingInterceptor());
        registry.addInterceptor(i18NConfiguration.localeChangeInterceptor());

        super.addInterceptors(registry);
    }


    @Bean
    public HandlerInterceptor viewObjectAddingInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {

                /* response time */
                svh.setStartTime(System.currentTimeMillis());

                /* server root path */
                if(svh.getPath() == null) {
                    svh.setPath(request.getContextPath());
                }

                /* current username */
                if(svh.getUsername() == null) {
                    SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                            .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
                    if(securityContextImpl != null && securityContextImpl.getAuthentication() != null &&
                            securityContextImpl.getAuthentication().getName() != null) {

                        svh.setUsername(securityContextImpl.getAuthentication().getName());
                    }
                }

                /* locale language */


                return true;
            }

            @Override
            public void postHandle(
                    HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
                    throws Exception {

                Cookie[] cookies = request.getCookies();

                svh.setLanguage("auto");
                if(cookies != null) {
                    for (Cookie c : cookies) {
                        if (c.getName().equals("BATMAN")) {
                            svh.setLanguage(c.getValue());
                        }
                    }
                }
            }
        };
    }
}
