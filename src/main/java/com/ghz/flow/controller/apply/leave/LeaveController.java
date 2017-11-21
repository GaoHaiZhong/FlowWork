package com.ghz.flow.controller.apply.leave;


import com.ghz.flow.base.pojo.FlowBean;
import com.ghz.flow.base.pojo.Roler;
import com.ghz.flow.base.pojo.User;
import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.service.ApplicationService;
import com.ghz.flow.service.FlowService;
import com.ghz.flow.service.LoginService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 这是针对请假流程 key 的操作(这里需要修改，暂时这样写)
 */
@Controller
@RequestMapping(value = "/leaveMail")
public class LeaveController {

    @Resource
    private LoginService loginService;
    @Resource
    private ApplicationService applicationService;
    @Resource
    private FlowService flowService;
    @Resource
    private FlowWorkServiceImpl flowWorkServiceImpl;

    //查看流转记录
    @RequestMapping(value = "/toApprovedHistoryUI")
    public String toApprovedHistoryUI(@RequestParam(value = "processInstanceId", required = true) String processInstanceId,
                                      HttpServletRequest request) {
        //开启流程流程实例，引入流程变量
        //查看流转记录，动态的的审批
        //审批通过
        /** 提交给上一级
         *  直到上一级结束为止
         *
         */
        List commentList = flowService.getCommentList(processInstanceId);
        request.setAttribute("commentList", commentList);
        //审批不通过


        return "/Flow_FormFlow/approvedHistory";
    }


    //审批处理,跳转到审批处理的页面
    @RequestMapping(value = "approve")
    public String approve(@RequestParam(value = "taskId", required = true) String taskId, HttpServletRequest request) {
        request.setAttribute("id", taskId);
        return "/Approve/approveUI";
    }

    //审批成功
    @RequestMapping(value = "/approveSucess")
    public String approveSucess(@RequestParam(value = "id", required = true) String taskId,
                                @RequestParam(value = "comment", required = true) String comment,
                                @RequestParam(value = "approval", required = true) String approval,
                                HttpServletRequest request) throws Exception {

        //根据id查询申请的相关
           /*Application application=applicationService.selectById(applicationId);
           User user=(User) request.getSession().getAttribute("user");
           SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-HH  hh:mm");
            String s_date =dateFormat.format(new Date());
           //创建审批的资料
           Approveinfo approveinfo=new Approveinfo();
           approveinfo.setUser(user);
           approveinfo.setAppli(application);
           approveinfo.setApplicant(user.getId());
           approveinfo.setApproval(approval);
           approveinfo.setApprovedate(dateFormat.parse(s_date));
           approveinfo.setApproveinfo(comment);
           approveinfo.setApplication(application.getId());
           approveService.insertApproveInfo(approveinfo);*/
        flowService.completeTaskAndComment(taskId, comment, approval);

        return "redirect:/approve/waitApprove.action";
    }

    @RequestMapping(value = "/followPircuter")
    public String followPircuter(@RequestParam(value = "id", required = true) String taskId,
                                 HttpServletRequest request) {
        //得到当前活动的实体
        ActivityImpl activityImpl = flowService.getActivityImplByTaskId(taskId);
        request.setAttribute("activityImpl", activityImpl);
        activityImpl.getProcessDefinition().getId();
        return "/image/image";
    }

    //这里要适应正在运行，和过去
    @RequestMapping(value = "/toTaskFormUI")
    public ModelAndView toTaskFormUI(@RequestParam(value = "taskId", required = true) String taskId
            , HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        TaskEntity taskEntity = flowService.selectTaskMessage(taskId);

        //查询本子类任务
        List<HistoricTaskInstance> subTasks = flowService.selectSubTaskByTaskId(taskId);

        //查询本父类任务
        HistoricTaskInstance parentTask = flowService.selectParentTaskByTaskId(taskId);


        //读取任务表当任务
        TaskFormData taskFormData = flowService.readTaskFrom(taskId);
        // 外置表单（如果getFormKey（）不为空则，应用了外置的表当）
        if (taskFormData != null && taskFormData.getFormKey() != null) {
            Object renderedTaskForm = flowWorkServiceImpl.getFormService().getRenderedTaskForm(taskId);

            model.addObject("taskFormData", renderedTaskForm);
            model.addObject("hasFormKey", true);//是否有外置表单，为true则表示有外置表单
        } else if (taskFormData != null) { // 动态表单
            model.addObject("taskFormData", taskFormData);

        } else {
            // 手动创建的任务（包括子任务）

            model.addObject("manualTask", true);
        }

        //得到任务的候选者
        List<IdentityLink> identityLinks = flowService.selectParticipantOrCandidate(taskId);

        //查询附件
        List<Attachment> attachments = flowService.selectAttachment(taskId);

        ProcessDefinitionEntity processDefinitionEntity = flowService.selectProcessDefinition(taskId);


        List<Roler> rolers = loginService.selectAllRoler();

        User user = (User) request.getSession().getAttribute("user");
        List<User> users = loginService.selectAllUser(user);
        FlowBean flowBean = new FlowBean();
        flowBean.setTaskEntity(taskEntity);
        flowBean.setProcessDefinitionEntity(processDefinitionEntity);

        model.addObject("flowBean", flowBean);
        //直接把子类的任务
        model.addObject("subTasks", subTasks);
        //父类任务
        model.addObject("parentTask", parentTask);

        //该任务的候选人，还有该流程实例的参与人
        model.addObject("identityLinks", identityLinks);
        model.addObject("users", users);
        model.addObject("groups", rolers);
        model.addObject("attachments", attachments);


        model.setViewName("/form/" + processDefinitionEntity.getKey() + "/task-form");

        return model;
    }

    /**
     * 根据任务的id签收任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/claimTask/{key}")
    public String claimTask(
            @PathVariable("key") String key,
            @RequestParam(value = "taskId", required = true) String taskId
    ) {
        //这里签收之后，就要改变删除原来候选人的位置，为
        flowService.claimTask(taskId);
        return "redirect:/" + key + "/toTaskFormUI.action?taskId=" + taskId;
    }

    /**
     * 退签
     *
     * @param key
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/ignsTask")
    public String ignsTask(@RequestParam(value = "key") String key,
                           @RequestParam(value = "taskId", required = true) String taskId) {
        flowService.ignsTask(taskId);

        return "redirect:/" + key + "/toTaskFormUI.action?taskId=" + taskId;
    }

    /**
     * 委办任务
     *
     * @return
     */
    @RequestMapping(value = "/setDelegateTask/{taskId}")
    @ResponseBody
    public String setDelegateTask(@PathVariable("taskId") String taskId,
                                  @RequestParam(value = "delegateUserId", required = true) String delegateUserId) {
        //将任务委托给其他人
        flowWorkServiceImpl.getTaskService().delegateTask(taskId, delegateUserId);
        return "success";
    }

    //进行审批
    @RequestMapping(value = "/completeTask/{taskId}")
    public String completeTask(@PathVariable("taskId") String taskId,
                               @RequestParam(value = "approval", required = true) String approval,
                               @RequestParam(value = "departLeadercomment", required = false) String departLeadercomment,
                               @RequestParam(value = "generalManagercomment", required = false) String generalManagercomment,
                               @RequestParam(value = "hrcomment", required = false) String hrcomment) {
        String commemt = null;
        if (StringUtils.isNoneBlank(departLeadercomment)) {
            commemt = "departLeadercomment." + departLeadercomment;
        }

        if (StringUtils.isNoneBlank(generalManagercomment)) {
            commemt = "generalManagercomment." + generalManagercomment;
        }
        if (StringUtils.isNoneBlank(hrcomment)) {
            commemt = "hrcomment." + hrcomment;
        }
        return flowService.completeTask(taskId, approval, commemt);
    }


    /**
     * 秘书协助处理
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/helpWork")
    public void helpWork(@RequestParam(value = "taskId", required = true) String taskId) {

        Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .taskId(taskId).singleResult();

        flowWorkServiceImpl.getRuntimeService().messageEventReceived("help_message", task.getExecutionId());

    }

    @RequestMapping(value = "/xiaoJia/{taskId}")
    public String xiaoJia(@PathVariable("taskId") String taskId) {

        flowWorkServiceImpl.getTaskService().complete(taskId);

        return "redirect:/approve/waitApprove";
    }


}
