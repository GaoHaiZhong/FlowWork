package com.ghz.flow.base.pojo;

import com.github.pagehelper.PageInfo;
import org.activiti.engine.impl.persistence.entity.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admi on 2017/8/27.
 */
@Repository
public class FlowBean {

    //历史实例任务对象
    private HistoricTaskInstanceEntity historicTaskInstanceEntity;
    //历史活动实例对象
    private HistoricActivityInstanceEntity historicActivityInstanceEntity;
    //申请对象
    private Application application;

    private String processInstanceId;
    //评论实例对象
    private List<Comment> commentList;
    //运行任务对象
    private TaskEntity taskEntity;  //任务的实体类
    //流程定义实体对象
    private ProcessDefinitionEntity processDefinitionEntity;
    //执行对象
    private  ExecutionEntity executionEntity;

    private ProcessInstance  processInstance;

    private HistoricProcessInstanceEntity historicProcessInstanceEntity;

    private PageInfo pageInfo;




    public void setHistoricTaskInstanceEntity(HistoricTaskInstanceEntity historicTaskInstanceEntity) {
        this.historicTaskInstanceEntity = historicTaskInstanceEntity;
    }

    public void setHistoricActivityInstanceEntity(HistoricActivityInstanceEntity historicActivityInstanceEntity) {
        this.historicActivityInstanceEntity = historicActivityInstanceEntity;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }



    public void setTaskEntity(TaskEntity taskEntity) {
        this.taskEntity = taskEntity;
    }

    public void setProcessDefinitionEntity(ProcessDefinitionEntity processDefinitionEntity) {
        this.processDefinitionEntity = processDefinitionEntity;
    }

    public void setExecutionEntity(ExecutionEntity executionEntity) {
        this.executionEntity = executionEntity;
    }

    public HistoricTaskInstanceEntity getHistoricTaskInstanceEntity() {
        return historicTaskInstanceEntity;
    }

    public HistoricActivityInstanceEntity getHistoricActivityInstanceEntity() {
        return historicActivityInstanceEntity;
    }

    public Application getApplication() {
        return application;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public TaskEntity getTaskEntity() {
        return taskEntity;
    }

    public ProcessDefinitionEntity getProcessDefinitionEntity() {
        return processDefinitionEntity;
    }

    public ExecutionEntity getExecutionEntity() {
        return executionEntity;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public HistoricProcessInstanceEntity getHistoricProcessInstanceEntity() {
        return historicProcessInstanceEntity;
    }

    public void setHistoricProcessInstanceEntity(HistoricProcessInstanceEntity historicProcessInstanceEntity) {
        this.historicProcessInstanceEntity = historicProcessInstanceEntity;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
