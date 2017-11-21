package com.ghz.flow.controller.job;

import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by admi on 2017/9/17.
 */

/**
 * 引擎配置参数
 */
@Controller
@RequestMapping("/engineInfo")
public class EnginessInfoContorller {
    @Resource
    private FlowWorkServiceImpl flowWorkServiceImpl;

    @RequestMapping("/toEngineInfoUI")
    public ModelAndView toEngineInfoUI(){

        ModelAndView modelAndView=new ModelAndView("/processDefinitionView/engine-info");
        //流程的属性
        Map<String, String> engineProperties = flowWorkServiceImpl.getManagementService().getProperties();
        modelAndView.addObject("engineProperties", engineProperties);

        ///系统的属性
        Map<String,String> systemProperties = new HashMap<String, String>();
        Properties properties = System.getProperties();
        Set<Object> objects = properties.keySet();
        for(Object object:objects){
            systemProperties.put(object.toString(),properties.get(object.toString()).toString());
        }
        modelAndView.addObject("systemProperties", systemProperties);

        return modelAndView;
    }

}
