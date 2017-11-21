package com.ghz.flow.controller.job;

import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.base.util.Page;
import com.ghz.flow.base.util.PageUtil;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程实例控制器
 */
@Controller
@RequestMapping(value = "proInSTManager")
public class ProcessInstanceManagerController {

    @Resource
    private FlowWorkServiceImpl   flowWorkServiceImpl;

    @RequestMapping(value = "toProcessInstanceUI")
    public  String   toProcessInstanceUI(HttpServletRequest request){
         //每页显示5条记录
        Page<ProcessInstance>  page=new Page<ProcessInstance>(PageUtil.PAGE_SIZE);

        int[] param = PageUtil.init(page, request);
        ProcessInstanceQuery processInstanceQuery = flowWorkServiceImpl.getRuntimeService().createProcessInstanceQuery()
                .orderByProcessInstanceId().asc();

        List<ProcessInstance> list = processInstanceQuery.listPage(param[0],param[1]);
        Map<String,ProcessDefinition> map=new HashMap<String, ProcessDefinition>();
        for(ProcessInstance processInstance:list){
            definitionCache(map,processInstance.getProcessDefinitionId());
            //流程是否挂起，激活
            boolean suspended = processInstance.isSuspended();
        }
        page.setResult(list);
        //设置页面的总数
        page.setTotalCount(processInstanceQuery.count());
        request.setAttribute("page",page);
        request.setAttribute("definitions",map);

        return "/processDefinitionView/processinstance-list";
    }


    @RequestMapping("/{state}/{processInstanceId}")
    public  String changeState(@PathVariable("state")String state,
                               @PathVariable("processInstanceId")String processInstanceId){
        //激活流程实力
        if(StringUtils.equals("active",state)){

            flowWorkServiceImpl.getRuntimeService().activateProcessInstanceById(processInstanceId);
            //睡眠流程实例
        }else {
            flowWorkServiceImpl.getRuntimeService().suspendProcessInstanceById(processInstanceId);

        }

        return "redirect:/proInSTManager/toProcessInstanceUI.action";
    }

    @RequestMapping("/deleteProceInst/{processInstanceId}")
    public  String  deleteProceInst(@PathVariable("processInstanceId")String processInstanceId,
                                    @RequestParam(value = "deleteReason",required = false)String deleteReason){

      flowWorkServiceImpl.getRuntimeService().deleteProcessInstance(processInstanceId,deleteReason);

        return "redirect:/proInSTManager/toProcessInstanceUI.action";
    }

    public void definitionCache(Map<String,ProcessDefinition> map, String processDefinitionId){
       if(map.get(processDefinitionId)==null) {
           ProcessDefinition processDefinition = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery()
                   .processDefinitionId(processDefinitionId).singleResult();

            map.put(processDefinitionId,processDefinition);

       }

    }






}
