package com.ghz.flow.base.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


public class Request {


    public static  HttpServletRequest getRequest(){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder

                .getRequestAttributes()).getRequest();
        return request;
    }



}
