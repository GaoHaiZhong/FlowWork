package com.ghz.flow.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**自定义拦截器
 * Created by admi on 2017/10/12.
 */
public class HandlerInterceptor1 implements HandlerInterceptor {
    //进入 Handler方法之前执行  即准备
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        System.out.println("----------"+httpServletRequest.getRequestURL()+"---------");
        return true;
    }

    //preHandle之后，在返回ModelAndView之前
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {


        System.out.println("=========  postHandles===============");
    }

    //执行Handler完成执行此方法
    //应用场景：统一异常处理，统一日志处理
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        System.out.println("=========  afterCompletion===============");
    }
}
