package com.ghz.flow.service.impl;


import com.ghz.flow.base.mapper.ApplicationMapper;
import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.*;
import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.base.util.ApplicationStatus;
import com.ghz.flow.base.util.Request;
import com.ghz.flow.exception.CustomException;
import com.ghz.flow.service.ApplicationService;
import com.ghz.flow.service.FlowService;
import com.ghz.flow.service.LoginService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.ghz.flow.base.util.Request.getRequest;


/**
 * Created by admi on 2017/7/27.
 */
@SuppressWarnings("InfiniteLoopStatement")
@Service
public class FlowServiceImpl implements FlowService {

    private static String TASK_LIST = "redirect:/approve/waitApprove.action";

    @Resource
    private FlowWorkServiceImpl flowWorkServiceImpl;

    @Resource
    private ApplicationService applicationService;
    @Resource
    private LoginService loginService;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ApplicationMapper applicationMapper;

    /**
     * 1，指定下一个任务的办理人（通过流程变量）
     * 2，使业务和流程相关联
     *
     * @param pdkey         key的值
     * @param applicationId 业务键
     * @return
     */
    public ProcessInstance startProcessInstance(Leave leave, String pdkey, Integer applicationId) {
        HttpServletRequest request = getRequest();
        User user = (User) request.getSession().getAttribute("user");
        //.getClass().simpleName();  类名。ID
        flowWorkServiceImpl.getIdentityService().setAuthenticatedUserId(user.getLoginname());
        Map<String, String> map = new HashMap<String, String>();
      /*  map.put("applyUserId",user.getLoginname());*/
        if (leave != null) {
            map.put("startDate", leave.getStartTime());
            map.put("endDate", leave.getEndTime());
            map.put("leaveType", leave.getLeaveType());
            map.put("reason", leave.getReason());
        }
        //组建业务主键
        String business_key = this.makeBussinessKey(applicationId);

        ProcessDefinition processDefinition = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionKey(pdkey).latestVersion().singleResult();
        //这里采用表单启动的方式来开启流程实例
        ProcessInstance processInstance = flowWorkServiceImpl.getFormService().submitStartFormData(processDefinition.getId(), business_key, map);
        String taskId = this.getTaskId(processInstance.getProcessInstanceId(), "提交申请");
        //完成提交申请
        flowWorkServiceImpl.getTaskService().complete(taskId);

        return processInstance;

    }

    public String getTaskId(String processInstanceId, String taskName) {
        Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .processInstanceId(processInstanceId).taskName(taskName)
                .singleResult();
        return task.getId();
    }

    /**
     * 提交表单
     *
     * @param taskId
     * @param approval
     * @return
     */
    public String completeTask(String taskId, String approval, String comment) {
        User user = (User) Request.getRequest().getSession().getAttribute("user");
        Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .taskId(taskId).singleResult();
        // 如果任务的流程定义任务Key为空则认为是手动创建的任务
        if (StringUtils.isBlank(task.getTaskDefinitionKey())) {
            flowWorkServiceImpl.getTaskService().complete(taskId);
            return TASK_LIST;
        }

        //如果是委派任务
        if (task.getDelegationState() == DelegationState.PENDING) {
            flowWorkServiceImpl.getTaskService().resolveTask(taskId);
            ProcessDefinitionEntity processDefinitionEntity = this.selectProcessDefinition(taskId);
            String key = processDefinitionEntity.getKey();
            return "redirect:/" + key + "/toTaskFormUI.action?taskId=" + taskId;

        }
        //判断当前任务的办理人是否一样 这里要记录退回的记录和不通过的记录都要登记
        if (StringUtils.equals(task.getAssignee(), user.getLoginname())) {
            flowWorkServiceImpl.getTaskService().setVariable(taskId, "approval", approval);
            Map<String, String> map = null;
            if (StringUtils.isNoneBlank(comment)) {
                String[] split = StringUtils.split(comment, ".");
                flowWorkServiceImpl.getTaskService().setVariable(taskId, split[0], split[1]);
                //把评论添加到评论表中到中去
                flowWorkServiceImpl.getTaskService().addComment(taskId, null, split[1]);
                map = new HashMap<String, String>();
                map.put(split[0], split[1]);
            }
            flowWorkServiceImpl.getFormService().submitTaskFormData(taskId, map);
            //得到的当前的申请Id;,且设置当前申请的状态
            List<ProcessInstance> list = flowWorkServiceImpl.getRuntimeService().createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId()).list();
            for(ProcessInstance processInstance :list){
                if(processInstance.getBusinessKey()!=null){
                    String keyByProcessInstance =
                            this.getBusinessKeyByProcessInstance(processInstance.getBusinessKey());
                    String[] split = keyByProcessInstance.split("\\.");
                    Application application = applicationService.selectById(Integer.parseInt(split[1]), null);
                    String applicationStatusByApprove = ApplicationStatus.getApplicationStatusByApprove(approval);
                    application.setStatus(applicationStatusByApprove);
                    applicationService.updateApplication(application);
                }

            }
            return TASK_LIST;
        } else {

            try {
                throw new CustomException("error,没有权限，不能完成该任务！");
            } catch (CustomException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public void nextTaskassignee() {
        User nextTaskUser = loginService.findNextUserBythisId();
        //这里先用流程变量的来设置下一个任务的办理人


    }

    public Map<String, List<FlowBean>> getHisTaskByloginName() {
        Application application = null;
        Map<String, List<FlowBean>> map = new HashMap<String, List<FlowBean>>();
        List<FlowBean> rejectApplicationList = new ArrayList<FlowBean>();
        List<FlowBean> applicationList = new ArrayList<FlowBean>();
        User user = (User) getRequest().getSession().getAttribute("user");
        //得到历史和申请的实体，当前用户的
        List<HistoricTaskInstance> list = flowWorkServiceImpl.getHistoryService().createHistoricTaskInstanceQuery()
                .taskName("提交申请").taskAssignee(user.getLoginname())
                .orderByHistoricTaskInstanceEndTime().desc().list();
        //判断是否被撤回   （如果涉及到在集合中添加元素的用迭代器）
        Iterator<HistoricTaskInstance> iterator = list.iterator();
        while (iterator.hasNext()) {
            HistoricTaskInstance historicTaskInstance = iterator.next();
            if (historicTaskInstance != null) {
                String processInstanceId = historicTaskInstance.getProcessInstanceId();
                HistoricProcessInstance historicProcessInstance = flowWorkServiceImpl.getHistoryService().createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();

                //查未结束的流程
                String businessKey = historicProcessInstance.getBusinessKey();
                String s = businessKey.split("\\.")[1];
                if (s != null) {
                    application = applicationService.selectById(Integer.parseInt(s), null);
                 /* //这里就设置可状态（不应该在这里设置）
                    if (historicProcessInstance.getEndTime() != null) {
                        application.setStatus(Application.STATUS_APPROVED);
                        applicationService.updateApplication(application);
                    }*/
                }
                System.out.println("s==================================" + s);

                //如果该流程实例结束，则申请状态改为完成


                FlowBean flowBean = new FlowBean();
                flowBean.setApplication(application);
                flowBean.setHistoricTaskInstanceEntity((HistoricTaskInstanceEntity) historicTaskInstance);
                //如果驳回的申请

                application = flowBean.getApplication();
                if (application.getRejecttime() != null && application.getRejecttime() > 0) {
                    rejectApplicationList.add(flowBean);
                } else {
                    //申请的
                    applicationList.add(flowBean);
                }

            }
        }

        map.put("rejectApplicationList", rejectApplicationList);
        map.put("applicationList", applicationList);

          /*  }*/
        return map;
    }

    /**
     * 查询待我审批（这里就需要的根据实际情况来选择，候选人）
     *
     * @return
     */
    public List<FlowBean> getCurrentTask() {
        Application application = null;
        List<FlowBean> taskViews = new ArrayList<FlowBean>();
        User user = (User) getRequest().getSession().getAttribute("user");
        List<Task> list = new ArrayList<Task>();
        //读取直接分配给当前人和已经签收的任务
        List<Task> doingTasks = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .taskAssignee(user.getLoginname()).orderByTaskCreateTime().desc().list();

        //受邀任务（参与人，候选人，以及某个候选组中的人）
        List<Task> InvolveTasks = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .taskInvolvedUser(user.getLoginname()).list();
        //如果查出来是同任务实例 (用遍历方式)
        for (int i = 0; i < doingTasks.size(); i++) {
            for (int j = 0; j < InvolveTasks.size(); j++) {
                Task doingTask = doingTasks.get(i);
                Task InvolveTask = InvolveTasks.get(j);
                //判断任务的Id是否相同，如果任务ID相同，则判断任务的处理人是否空
                if (doingTask.getId().equals(InvolveTask.getId()) && doingTask.getAssignee() != null) {
                    InvolveTasks.remove(j);
                }
            }
        }
        //合并
        list.addAll(doingTasks);
        list.addAll(InvolveTasks);
        //foreach换选用这个，因为foreach里不能有add和remove
        Iterator<Task> iterator = list.iterator();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            if (task != null) {

                if (task.getId() != "" || !("").equals(task.getId())) {
                    //任务的parentId为空时，
                    String processInstanceId = "";
                    if (task.getParentTaskId() != null) {
                        Task parentTask = flowWorkServiceImpl.getTaskService().createTaskQuery().taskId(task.getParentTaskId()).singleResult();
                        processInstanceId = parentTask.getProcessInstanceId();
                    } else {
                        processInstanceId = task.getProcessInstanceId();
                    }
                    ProcessInstance processInstance = flowWorkServiceImpl.getRuntimeService().createProcessInstanceQuery()
                            .processInstanceId(processInstanceId).singleResult();
                    ProcessDefinition processDefinition = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery()
                            .processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
                    String businessKey = processInstance.getBusinessKey();
                    if (businessKey == null || ("").equals(businessKey)) {
                        application = new Application();
                    } else {
                        String applicationId = businessKey.split("\\.")[1];
                        application = applicationService.selectById(Integer.parseInt(applicationId), null);

                    }
                    user = loginService.selectById(application.getApplicant() + "");
                    application.setUser(user);
                    System.out.println(" application.getUser().getLoginname()" + application.getUser().getLoginname());
                    FlowBean flowBean = new FlowBean();
                    flowBean.setApplication(application);
                    flowBean.setTaskEntity((TaskEntity) task);
                    flowBean.setProcessDefinitionEntity((ProcessDefinitionEntity) processDefinition);
                    taskViews.add(flowBean);
                }
            }

        }
        return taskViews;
    }

    public List<FlowBean> getHistoric() {
        String processInstanceId = null;
        List<FlowBean> histoticList = new ArrayList<FlowBean>();
        User user = (User) getRequest().getSession().getAttribute("user");
        //登陆用户审批完成的记录
        List<HistoricTaskInstance> historicTaskList = flowWorkServiceImpl.getHistoryService().createHistoricTaskInstanceQuery()
                .taskAssignee(user.getLoginname()).orderByHistoricTaskInstanceEndTime().desc().finished().list();
        //foreach换选用这个，因为foreach里不能有add和remove
        Iterator<HistoricTaskInstance> iterator = historicTaskList.iterator();
        while (iterator.hasNext()) {
            HistoricTaskInstance historicTaskInstance = iterator.next();
            if (historicTaskInstance != null) {
                //考虑的历史子任务的查询
                if (StringUtils.isNoneBlank(historicTaskInstance.getParentTaskId())) {
                    HistoricTaskInstance historicTaskInstance1 = flowWorkServiceImpl.getHistoryService().createHistoricTaskInstanceQuery()
                            .taskId(historicTaskInstance.getParentTaskId()).singleResult();
                    processInstanceId = historicTaskInstance1.getProcessInstanceId();
                } else {
                    processInstanceId = historicTaskInstance.getProcessInstanceId();
                }

                HistoricProcessInstance historicProcessInstance = flowWorkServiceImpl.getHistoryService().createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstanceId).singleResult();
                ProcessDefinition processDefinition = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery()
                        .processDefinitionId(historicProcessInstance.getProcessDefinitionId()).singleResult();

                String businessKey = historicProcessInstance.getBusinessKey();
                String s = businessKey.split("\\.")[1];
                Application application = applicationService.selectById(Integer.parseInt(s), null);
                System.out.println("getHistoric=================================" + application.getTitle());
                user = loginService.selectById(application.getApplicant() + "");
                application.setUser(user);
                FlowBean flowBean = new FlowBean();
                flowBean.setHistoricTaskInstanceEntity((HistoricTaskInstanceEntity) historicTaskInstance);
                flowBean.setApplication(application);
                flowBean.setProcessDefinitionEntity((ProcessDefinitionEntity) processDefinition);
                flowBean.setHistoricProcessInstanceEntity((HistoricProcessInstanceEntity) historicProcessInstance);
                histoticList.add(flowBean);

            }
        }
        /*for (HistoricTaskInstance historicTaskInstance : historicTaskList) {


        }*/

        return histoticList;
    }

    public void completeTaskAndComment(String taskId, String comment, String approval) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) getRequest().getSession().getAttribute("user");
        Authentication.setAuthenticatedUserId(user.getLoginname());
        Task currTask = flowWorkServiceImpl.getTaskService().
                createTaskQuery().taskId(taskId).singleResult();
        ProcessInstance processInstance = flowWorkServiceImpl.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId(currTask.getProcessInstanceId()).singleResult();
        Execution execution = flowWorkServiceImpl.getRuntimeService().createExecutionQuery()
                .processInstanceId(currTask.getProcessInstanceId()).singleResult();
        String businessKey = processInstance.getBusinessKey();
        String s = businessKey.split("\\.")[1];
        Application application = applicationService.selectById(Integer.parseInt(s), null);
        flowWorkServiceImpl.getTaskService().addComment(taskId, null, comment);

        //驳回
        if (approval.equals("2")) {
            Integer rejecttime = application.getRejecttime();
            //
            if (rejecttime >= 3) {
                map.put("approval", "3");
                System.out.println(approval.toString());
                flowWorkServiceImpl.getTaskService().complete(taskId, map);
                application.setStatus(Application.STATUS_UNAPPROVED);
                applicationService.updateApplication(application);
            } else {
                map.put("approval", approval);
                application.setRejecttime(rejecttime + 1);
                applicationService.updateApplication(application);
            }

            flowWorkServiceImpl.getTaskService().complete(taskId, map);

            //通过
        } else if (approval.equals("1")) {
            if (processInstance != null) {
                User nextUserBythisId = loginService.findNextUserBythisId();
                //这里应该写同样的审批人，节点超过三个时：map.put("approver",nextUserBythisId.getLoginname());
              /*  map.put("HrLeader",nextUserBythisId.getLoginname());*/
                map.put("approval", approval);
            } else {
                application.setStatus(Application.STATUS_UNAPPROVED);
                applicationService.updateApplication(application);

            }
            flowWorkServiceImpl.getTaskService().complete(taskId, map);

            //不同意
        } else {
            map.put("approval", approval);
            System.out.println(approval.toString());
            application.setStatus(Application.STATUS_UNAPPROVED);
            applicationService.updateApplication(application);
            flowWorkServiceImpl.getTaskService().complete(taskId, map);
        }

    }

    /**
     * 考虑到多实例
     *
     * @param taskId 任务Id
     * @return
     */

    public ActivityImpl getActivityImplByTaskId(String taskId) {
        Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .taskId(taskId).singleResult();
        String processDefinitionId = task.getProcessDefinitionId();
        String executionId = task.getExecutionId();
        Execution execution = flowWorkServiceImpl.getRuntimeService().createExecutionQuery()
                .executionId(executionId).singleResult();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) flowWorkServiceImpl.getRepositoryService()
                .getProcessDefinition(processDefinitionId);

        ActivityImpl activity = processDefinitionEntity.findActivity(execution.getActivityId());


        return activity;
    }

    public List getCommentList(String processInstanceId) {
        List<FlowBean> commentList = new ArrayList<FlowBean>();
        //  2）通过流程实例查询所有的(用户任务类型)历史活动
        List<HistoricActivityInstance> list = flowWorkServiceImpl.getHistoryService().createHistoricActivityInstanceQuery()
                .activityType("userTask").processInstanceId(processInstanceId)
                .finished().orderByHistoricActivityInstanceEndTime().desc().list();
        Iterator<HistoricActivityInstance> iterator = list.iterator();
        while (iterator.hasNext()) {
            HistoricActivityInstance historicActivityInstance = iterator.next();
            if (historicActivityInstance != null) {
                String historicTaskId = historicActivityInstance.getTaskId();
                List<Comment> taskComments = flowWorkServiceImpl.getTaskService().getTaskComments(historicTaskId);
                System.out.println(taskComments.toString());
                FlowBean flowBean = new FlowBean();
                flowBean.setCommentList(taskComments);
                flowBean.setHistoricActivityInstanceEntity((HistoricActivityInstanceEntity) historicActivityInstance);
                commentList.add(flowBean);
            }
        }
      /*  for (HistoricActivityInstance historicActivityInstance : list) {

        }*/
        return commentList;
    }


    /**
     * 针对流程实例来说，（子流程来说可以获取不到key）
     *
     * @param processInstanceId
     * @return
     */
    public String getBusinessKeyByProcessInstance(String processInstanceId) {
        String s = null;
        if (processInstanceId != null) {
            HistoricProcessInstance historicProcessInstance = flowWorkServiceImpl.getHistoryService().createHistoricProcessInstanceQuery()
                    .processInstanceId(processInstanceId).singleResult();
            String businessKey = historicProcessInstance.getBusinessKey();
            s = businessKey.split("\\.")[1];

        }


        return s;
    }

    public String makeBussinessKey(Integer applicationId) {
        Application application = applicationService.selectById(applicationId, null);
        String simpleName = application.getClass().getSimpleName();
        String business_key = simpleName + "." + applicationId;
        return business_key;
    }

    public void claimTask(String taskId) {
        User user = (User) Request.getRequest().getSession().getAttribute("user");
        flowWorkServiceImpl.getTaskService().claim(taskId, user.getLoginname());

    }

    public TaskEntity selectTaskMessage(String taskId) {
        //查询正在运行
        if (taskId != null) {

            Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                    .taskId(taskId).singleResult();


            return (TaskEntity) task;
        }

        return null;

    }

    public List<IdentityLink> selectParticipantOrCandidate(String taskId) {
        List<IdentityLink> identityLinksForTask = flowWorkServiceImpl.getTaskService().getIdentityLinksForTask(taskId);
        return identityLinksForTask;
    }

    public void addParticipants(String[] usersId, String[] types, String taskId) {
        User user = (User) Request.getRequest().getSession().getAttribute("user");
        flowWorkServiceImpl.getIdentityService().setAuthenticatedUserId(user.getLoginname());
        for (int i = 0; i < usersId.length; i++) {

            flowWorkServiceImpl.getTaskService().addUserIdentityLink(taskId, usersId[i], types[i]);

        }
    }

    public void addCandidateByType(String[] types, String taskId, String[] userOrGroupIds) {
        for (int i = 0; i < types.length; i++) {
            String type = types[i];
            if (("user").equals(type)) {
                flowWorkServiceImpl.getTaskService().addCandidateUser(taskId, userOrGroupIds[i]);

            } else if (("group").equals(type)) {
                flowWorkServiceImpl.getTaskService().addCandidateGroup(taskId, userOrGroupIds[i]);
            }
        }
    }

    public void deleteParticipant(String taskId, String userId, String type, String groupId) {
        //判断类型
        //判断字符串是否为空，null(一般来判断是否表单)
        if (StringUtils.isNoneBlank(groupId)) {
            //删除候选组
            flowWorkServiceImpl.getTaskService().deleteCandidateGroup(taskId, groupId);
        } else {
            //删除用户相关的用户
            flowWorkServiceImpl.getTaskService().deleteUserIdentityLink(taskId, userId, type);
        }
    }

    public void ignsTask(String taskId) {
        //要退签之前要验证是否有候选人，或者候选组，如果没有则不能退签
        List<IdentityLink> identityLinks = this.selectParticipantOrCandidate(taskId);
        for (IdentityLink identityLink : identityLinks) {
            //是否存候选组，候选人
            if (StringUtils.equals(IdentityLinkType.CANDIDATE, identityLink.getType())) {
                flowWorkServiceImpl.getTaskService().claim(taskId, null);
                break;
            }
        }
        try {
            throw new CustomException("没有候选人，不可退签");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProcessDefinitionEntity selectProcessDefinition(String taskId) {
        Task task = flowWorkServiceImpl.getTaskService().createTaskQuery().
                taskId(taskId).singleResult();
        String processInstanceId = null;
        if (task.getParentTaskId() != null) {
            Task parentTask = flowWorkServiceImpl.getTaskService().createTaskQuery().taskId(task.getParentTaskId()).singleResult();
            processInstanceId = parentTask.getProcessInstanceId();
        } else {
            processInstanceId = task.getProcessInstanceId();
        }
        ProcessInstance processInstance = flowWorkServiceImpl.getRuntimeService().createProcessInstanceQuery()
                .processInstanceId(processInstanceId).singleResult();
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery()
                .processDefinitionId(processInstance.getProcessDefinitionId()).singleResult();
        return processDefinitionEntity;
    }

    public ProcessDefinitionEntity addSubTask(String parentId, String taskName, String description) {
        ProcessDefinitionEntity processDefinitionEntity = this.selectProcessDefinition(parentId);
        Task task = flowWorkServiceImpl.getTaskService().newTask();
        task.setParentTaskId(parentId);//父类的Id
        User user = (User) Request.getRequest().getSession().getAttribute("user");
        task.setOwner(user.getLoginname());  //设置任务拥有人
        task.setAssignee(user.getLoginname()); //设置任务的办理人
        task.setName(taskName);  //设置任务的名字
        task.setDescription(description);  //设置任务描述
        flowWorkServiceImpl.getTaskService().saveTask(task);

        return processDefinitionEntity;
    }

    public List<HistoricTaskInstance> selectSubTaskByTaskId(String taskId) {
        if (taskId != null && !("").equals(taskId)) {
            List<HistoricTaskInstance> list = flowWorkServiceImpl.getHistoryService().createHistoricTaskInstanceQuery()
                    .taskParentTaskId(taskId).list();
            return list;
        } else {
            return null;
        }

    }

    public HistoricTaskInstance selectParentTaskByTaskId(String taskId) {

        TaskEntity taskEntity = this.selectTaskMessage(taskId);
        if (taskEntity.getParentTaskId() != null && !("").equals(taskEntity.getParentTaskId())) {
            HistoricTaskInstance historicTaskInstance = flowWorkServiceImpl.getHistoryService().createHistoricTaskInstanceQuery()
                    .taskId(taskEntity.getParentTaskId()).singleResult();
            return historicTaskInstance;
        } else {
            return null;
        }

    }

    public void addAttachmenFile(MultipartFile file, String taskId, String processInstanceId, String attachmentName, String attachmentDescription) {

        try {//文件的类型
            String attachmentType = file.getContentType() + ";" + FilenameUtils.getExtension(file.getOriginalFilename());
            User user = (User) Request.getRequest().getSession().getAttribute("user");
            flowWorkServiceImpl.getIdentityService().setAuthenticatedUserId(user.getLoginname());
            //持久化附件
            Attachment attachment = flowWorkServiceImpl.getTaskService().createAttachment(attachmentType, taskId, processInstanceId, attachmentName, attachmentDescription,
                    file.getInputStream());
            //保存文件
            flowWorkServiceImpl.getTaskService().saveAttachment(attachment);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void addAttachmentUrl(String taskId, String processInstanceId, String attachmentName, String attachmentDescription, String url) {
        String attachmentType = "url";
        User user = (User) Request.getRequest().getSession().getAttribute("user");
        flowWorkServiceImpl.getIdentityService().setAuthenticatedUserId(user.getLoginname());
/*
     * 如果要更新附件内容，先读取附件对象，然后设置属性（只能更新name和description），最后保存附件对象
     */
//    taskService.saveAttachment(attachment);
        flowWorkServiceImpl.getTaskService().createAttachment(attachmentType, taskId, processInstanceId, attachmentName, attachmentDescription, url);

    }

    public List<Attachment> selectAttachment(String taskId) {
        List<Attachment> taskAttachments = flowWorkServiceImpl.getTaskService().
                getTaskAttachments(taskId);
        return taskAttachments;
    }

    public TaskEntity deleteSubTaskById(String taskId) {
        User user = (User) Request.getRequest().getSession().getAttribute("user");
        Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                .taskId(taskId).singleResult();
        flowWorkServiceImpl.getTaskService().deleteTask(taskId, "deleteByUser" + user.getLoginname());
        return (TaskEntity) task;
    }

    public void deleteAttachmentById(String attachmentId) {
        flowWorkServiceImpl.getTaskService().deleteAttachment(attachmentId);
    }

    public TaskFormData readTaskFrom(String taskId) {
        //得到任务的表单
        TaskFormData taskFormData = flowWorkServiceImpl.getFormService().getTaskFormData(taskId);


        return taskFormData;
    }

    public StartFormData readStartFrom(String processDefinitionId) {
        StartFormData startFormData = flowWorkServiceImpl.getFormService().getStartFormData(processDefinitionId);

        return startFormData;
    }

    //如何进行分页
    public Map<String, Object> getApplicationList(String status, int pageNum) {
        int pageSize = 10;
        Map<String, Object> map = new HashMap<String, Object>();
        User user = (User) Request.getRequest().getSession().getAttribute("user");
        List<FlowBean> pageList = new ArrayList<FlowBean>();
        //我的申请页面分页
      /*  Page<FlowBean> applicationPage=new Page<FlowBean>(PageUtil.PAGE_SIZE);
        int[] init1 = PageUtil.init(applicationPage, Request.getRequest());*/

        ApplicationExample applicationExample = new ApplicationExample();
        ApplicationExample.Criteria criteria = applicationExample.createCriteria();
        criteria.andApplicantEqualTo(user.getId());
        if (StringUtils.isNoneBlank(status)) {
            criteria.andStatusEqualTo(status);

        }

        //分页处理，显示第一页的10条数据
        PageHelper.startPage(pageNum, pageSize);
        List<Application> applications = applicationMapper.selectByExample(applicationExample);
        Iterator<Application> iterator = applications.iterator();
        //foreach换选用这个，因为foreach里不能有add和remove
        while (iterator.hasNext()) {
            Application application = iterator.next();
            if (application != null) {
                String bussinKey = application.getClass().getSimpleName() + "." + application.getId();
                HistoricProcessInstance historicProcessInstance = flowWorkServiceImpl.getHistoryService().createHistoricProcessInstanceQuery()
                        .processInstanceBusinessKey(bussinKey).singleResult();
                //由于驳回的时候会产生这些（不同id同一个历史任务信息）
                /*HistoricTaskInstance historicTaskInstance = flowWorkServiceImpl.getHistoryService().createHistoricTaskInstanceQuery().
                        processInstanceId(historicProcessInstance.getId())
                        .taskAssignee(user.getLoginname())
                        .taskName("提交申请")
                        .singleResult();*/
                //得到当前活动
                Task task = flowWorkServiceImpl.getTaskService().createTaskQuery()
                        .processInstanceId(historicProcessInstance.getId()).singleResult();
                FlowBean flowBean = new FlowBean();
                flowBean.setApplication(application);
           /*   flowBean.setHistoricTaskInstanceEntity((HistoricTaskInstanceEntity)
                      historicTaskInstance);*/
                flowBean.setTaskEntity((TaskEntity) task);
                flowBean.setHistoricProcessInstanceEntity((HistoricProcessInstanceEntity)historicProcessInstance);
                pageList.add(flowBean);
            }

        }

        PageInfo pageInfo = new PageInfo(applications);
        map.put("pageInfo", pageInfo);
        map.put("pageList", pageList);

        return map;

    }


}
