package com.ghz.flow.controller.job;

import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.base.util.Page;
import com.ghz.flow.base.util.PageUtil;
import org.activiti.engine.runtime.Job;
import org.activiti.engine.runtime.JobQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admi on 2017/9/17.
 */
@Controller
@RequestMapping("jobController")
public class JobController {

    @Resource
    private FlowWorkServiceImpl flowWorkServiceImpl;

    public static Map<String, String> JOB_TYPES = new HashMap<String, String>();

    //作业的类型
    static {
        JOB_TYPES.put("activate-processdefinition", "激活流程定义");
        JOB_TYPES.put("timer-intermediate-transition", "中间定时");
        JOB_TYPES.put("timer-transition", "边界定时");
        JOB_TYPES.put("timer-start-event", "定时启动流程");
        JOB_TYPES.put("suspend-processdefinition", "挂起流程定义");
        JOB_TYPES.put("async-continuation", "异步锁");
    }

    @RequestMapping(value = "/toJobList")
    public  ModelAndView   toJobList(HttpServletRequest request){
        ModelAndView modelAndView =new ModelAndView("/processDefinitionView/job-list");

        Page<Job> page=new Page<Job>(PageUtil.PAGE_SIZE);
        int[] init = PageUtil.init(page,request);
        JobQuery jobQuery = flowWorkServiceImpl.getManagementService().createJobQuery();
        List<Job> jobs = jobQuery.listPage(init[0], init[1]);
        //作业异常的map
        Map<String,String> exceptionMap=new HashMap<String, String>();
        for(Job job:jobs){
          if(StringUtils.isNoneBlank(job.getExceptionMessage())){
               exceptionMap.put(job.getId(),job.getExceptionMessage());
          }
        }
         page.setResult(jobs);
        page.setTotalCount(jobQuery.count());

        modelAndView.addObject("page",page);
        modelAndView.addObject("exceptionStacktraces",exceptionMap);
        modelAndView.addObject("JOB_TYPES", JOB_TYPES);
        return  modelAndView;
    }



    @RequestMapping("/deleteJob/{jobId}")
    public String deleteJob(@PathVariable("jobId")String jobId){
        flowWorkServiceImpl.getManagementService().deleteJob(jobId);
        return "redirect:/jobController/toJobList.action";
    }


    @RequestMapping("/executeJob/{jobId}")
    public  String  executeJob(@PathVariable("jobId")String jobId){
        flowWorkServiceImpl.getManagementService().executeJob(jobId);
        return "redirect:/jobController/toJobList.action";
    }

    @RequestMapping("/changeTime/{jobId}")
    public  String changeTime(@PathVariable("jobId")String jobId,
                              @RequestParam(value = "retries",required = true)Integer retries){
        flowWorkServiceImpl.getManagementService().setJobRetries(jobId,retries);
        return "redirect:/jobController/toJobList.action";
    }

}
