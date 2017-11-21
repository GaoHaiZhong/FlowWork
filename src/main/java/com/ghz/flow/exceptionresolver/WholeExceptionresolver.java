package com.ghz.flow.exceptionresolver;

import com.ghz.flow.exception.CustomException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *自定义异常解析器
 * Created by admi on 2017/10/12.
 */
public class WholeExceptionresolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        e.printStackTrace();
        CustomException customException=null;
        //如果抛出的是系统自定义异常则直接转换 判断e对象是否是CustomException的实例
        if(e instanceof CustomException ){
            customException=(CustomException) e;
        }else{
            customException= new CustomException("未知错误，请管理员联系");

        }
        ModelAndView modelAndView=new ModelAndView("error");
        modelAndView.addObject("message",customException.getMessage());




        return modelAndView;
    }
}
