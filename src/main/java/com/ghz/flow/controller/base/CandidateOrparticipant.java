package com.ghz.flow.controller.base;

import com.ghz.flow.service.FlowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by admi on 2017/9/3.
 */
@RequestMapping("/CandidateOrparticipant")
@Controller
public class CandidateOrparticipant {

    @Resource
    private FlowService flowService;

    /**
     * 添加参与者
     * @param taskId
     * @param userIds
     * @param types
     * @return
     */
    @RequestMapping(value = "/task/participant/add/{taskId}")
    @ResponseBody
    public  String  addParticipant(@PathVariable("taskId")String taskId,
                                   @RequestParam(value = "userId[]",required = true)String[] userIds
            ,@RequestParam(value="type[]",required = true)String[] types){
        flowService.addParticipants(userIds,types,taskId);
        return "success";
    }

    //添加候选组或者候选人
    @RequestMapping(value = "/addCandidate/add/{taskId}")
    @ResponseBody
    public String  addCandidateOrCandidateGroup(@PathVariable(value = "taskId")String taskId,
                                                @RequestParam(value = "userOrGroupIds[]")String[] userOrGroupIds,
                                                @RequestParam(value = "type[]")String[] types){

        flowService.addCandidateByType(types,taskId,userOrGroupIds);

        return "success";
    }

    /**
     * 删除参与人，候选人，候选组
     * @param taskId  任务的ID
     * @param userId    用户的Id
     * @param type     类型
     * @return
     */

    @RequestMapping(value = "/deleteParticipant/{taskId}")
    @ResponseBody
    public String   deleteParticipant(@PathVariable("taskId")String taskId,
                                      @RequestParam(value = "userId",required = false)String userId,
                                      @RequestParam(value = "groupId",required = false)String groupId,
                                      @RequestParam(value = "type")String type ){
        flowService.deleteParticipant(taskId,userId,type,groupId);

        return "success";
    }
}
