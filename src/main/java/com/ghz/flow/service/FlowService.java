package com.ghz.flow.service;


import com.ghz.flow.base.pojo.FlowBean;
import com.ghz.flow.base.pojo.Leave;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.IdentityLink;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * activiti 流程的Service
 */
public interface FlowService {

    /**
     * 通过Key值来启动流程实例（最新的流程实例）
     * @param pdkey  key的值
     * @param applicationId  业务键
     * @return   流程实例
     */
    public ProcessInstance startProcessInstance(Leave leave, String pdkey, Integer applicationId);

    /**
     *  根据流程实例查询任务的Id
     * @param processInstanceId
     *  @param taskName
     *
     */
    public  String   getTaskId(String processInstanceId,String taskName);

    /**
     * 根据任务的ID完成任务
     * @param taskId
     */
    public String completeTask(String taskId,String approval,String comment);

    /**
     *指定当前的用户的上级为他的办理人
     */
    public  void  nextTaskassignee();


    //查询当前提交为完成的

    /**
     * 查询登陆用户的所有申请
     * @return
     */
    public Map<String,List<FlowBean>> getHisTaskByloginName();

    /**
     * 得到驳回的申请
     * @return
     */
  /*  public  List<FlowBean>  getRejectApplicationList();*/


    /**
     * 得到
     */
   /* public  List<FlowBean> getApplicationList();*/

    /**
     * 获取当前用户的任务
     * @return
     */
    public List<FlowBean>  getCurrentTask();

    /**
     * 查询登陆用户完成的任务
     * @return
     */
    public List<FlowBean>  getHistoric();

    /**
     * 根据任务的ID 完成任务，并添加评论
     * @param taskIdS
     */

    public  void completeTaskAndComment(String taskIdS, String comment, String approval);

    /**
     * 根据当钱前的ID 查询当前任务，并获当前活动的的信息
     * @param taskId  任务Id
     * @return
     */
    public ActivityImpl getActivityImplByTaskId(String taskId);

    /**
     * 查看历史评论表
     * @param processInstanceId
     * @return
     */
    public List  getCommentList(String processInstanceId);

    /**
     *回退到上一级
     * @param taskId  任务的id
     */
  /*  public void backLastTask(String taskId, String Comment);*/

    /**
     * 回退到申请起点
     * @param taskId    任务的Id
     * @param destTaskName   退回Id的目的地
     */
  /*  public void backStartTask(String taskId, String destTaskName, Map<String, Object> map);*/


    /**
     *
     * 根据porcessInstanceI得到业务键
     * @param processInstanceId
     * @return
     */
    public  String   getBusinessKeyByProcessInstance(String processInstanceId);

    /**
     * 组建业务主键
     * @param applicationId
     * @return
     */
    public  String    makeBussinessKey(Integer applicationId);

    /**
     * 候选签收签收
     * @param taskId
     * @return
     */
    public  void  claimTask(String taskId);


    /**
     * 查询任务的信息
     * @param taskId  任务的Id
     * @return
     */
    public TaskEntity selectTaskMessage(String taskId);


    /**
     * 查询流程实例的参与者，和候选者
     * @param taskId
     * @return
     */
    List<IdentityLink> selectParticipantOrCandidate(String taskId);

    /**
     * 添加参与人
     * @param usersId  用户数组
     * @param types    用户类型
     * @param taskId   用户任务Id
     */
     void addParticipants(String[] usersId,String[] types,String taskId);

    /**
     * 根据类型添加候选人或者候选者
     * @param types   类型数组
     */
    void  addCandidateByType(String[] types,String taskId,String[] userOrGroupIds);

    /**
     * 删除参与者，候选人，候选组
     * @param taskId
     * @param userId
     * @param type
     * @param groupId
     */
    void deleteParticipant(String taskId,String userId,String type,String groupId);

    /**
     * 退签任务
     * @param taskId
     */
    void ignsTask(String taskId);

    /**
     * 根据任务的Id查询流程定义
     * @param taskId
     * @return
     */

    ProcessDefinitionEntity selectProcessDefinition(String taskId);

    /**
     * 为夫任务添加的任务
     * @param parentId
     * @param taskName
     * @param description
     */
    ProcessDefinitionEntity addSubTask(String parentId,String taskName,String description);

    /**
     * 根据父类的Id查询子流程
     * @param taskId
     * @return
     */
    List<HistoricTaskInstance> selectSubTaskByTaskId(String taskId);

    /**
     * 查询该任务的父任务
     * （查询父任务，及他本身就是子任务。可以通过查询子任务，得到parentId）
     * @param taskId
     * @return
     */
    HistoricTaskInstance selectParentTaskByTaskId(String taskId);

    /**
     * 添加文件类型的附件
     * @param taskId
     * @param processInstanceId
     * @param attachmentName
     * @param attachmentDescription
     * @return
     */
    void addAttachmenFile(MultipartFile file,String taskId, String processInstanceId, String attachmentName, String attachmentDescription);


    /**
     * 添加URL文件
     * @param taskId
     * @param processInstanceId
     * @param attachmentName
     * @param attachmentDescription
     * @param url
     */
    void addAttachmentUrl(String taskId, String processInstanceId, String attachmentName, String attachmentDescription, String url);

    /**
     * 查询附件
     * @param taskId
     * @return
     */
    List<Attachment> selectAttachment(String taskId);

    /**
     * 散出子任务
     * @param taskId
     */
    TaskEntity deleteSubTaskById(String taskId);

    /**
     * 通过Id删除附件
     * @param attachmentId
     */
    void deleteAttachmentById(String attachmentId);

    /**
     *读取任务的表当
     * @param taskId
     */
    TaskFormData readTaskFrom(String taskId);

    /**
     * 获得开始的的表单属性
     * @param processDefinitionId
     * @return
     */
    StartFormData readStartFrom(String processDefinitionId);


    Map<String, Object> getApplicationList(String status,int pageNum);
}
