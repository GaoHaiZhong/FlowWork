package com.ghz.flow.controller.base;

import com.ghz.flow.service.FlowService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

/**
 * Created by admi on 2017/9/3.
 */
@RequestMapping(value = "/subTask")
@Controller
public class SubTaskController {

    @Resource
    private FlowService flowService;

    /**
     * 添加
     * @param parentTaskId
     * @param taskName
     * @param description
     * @return
     */
    @RequestMapping(value = "/add/{taskId}")
    public  String addSubTask(@PathVariable("taskId")String parentTaskId,
                              @RequestParam(value = "taskName",required = false)String taskName,
                              @RequestParam(value = "description",required = false) String description){
        ProcessDefinitionEntity processDefinitionEntity = flowService.addSubTask(parentTaskId, taskName, description);
        String key = processDefinitionEntity.getKey();
        return "redirect:/"+key+"/toTaskFormUI.action?taskId="+parentTaskId;
    }


    @RequestMapping(value = "/deleteSubTask/{taskId}")
    public  String   deleteSubTask(@PathVariable("taskId") String taskId){

      ProcessDefinitionEntity processDefinitionEntity = flowService.selectProcessDefinition(taskId);
        String key = processDefinitionEntity.getKey();
        TaskEntity taskEntity = flowService.deleteSubTaskById(taskId);
        return "redirect:/"+key+"/toTaskFormUI.action?taskId="+taskEntity.getParentTaskId();

    }
}
