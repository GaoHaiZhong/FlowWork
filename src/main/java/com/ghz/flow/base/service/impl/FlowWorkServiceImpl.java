package com.ghz.flow.base.service.impl;

import org.activiti.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by admi on 2017/8/26.
 */
@Repository
public class FlowWorkServiceImpl {
     @Autowired
     private RepositoryService repositoryService;

    @Autowired
     private RuntimeService runtimeService;

    @Autowired
     private TaskService taskService;

    @Autowired
     private HistoryService historyService;

    @Autowired
     private ManagementService managementService;

    @Autowired
     private IdentityService identityService;

    @Autowired
    private FormService formService;


    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public RuntimeService getRuntimeService() {
        return runtimeService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public HistoryService getHistoryService() {
        return historyService;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    public ManagementService getManagementService() {
        return managementService;
    }

    public void setManagementService(ManagementService managementService) {
        this.managementService = managementService;
    }

    public IdentityService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public FormService getFormService() {
        return formService;
    }

    public void setFormService(FormService formService) {
        this.formService = formService;
    }
}
