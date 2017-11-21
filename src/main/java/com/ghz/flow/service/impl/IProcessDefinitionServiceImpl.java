package com.ghz.flow.service.impl;


import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.service.IProcessDefinitionService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.IdentityLink;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by admi on 2017/6/21.
 */
@SuppressWarnings("InfiniteLoopStatement")
@Service
public class IProcessDefinitionServiceImpl implements IProcessDefinitionService {

    @Resource
    private FlowWorkServiceImpl  flowWorkServiceImpl;


    //获得流程定义的列表  （根据流程的key值）
    public List<ProcessDefinition> findProcessDefinitionByLastKey() {

        ProcessDefinitionQuery processDefinitionQuery = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery();
        Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
        //流程的版本来升序
        processDefinitionQuery.orderByProcessDefinitionVersion().asc();
        List<ProcessDefinition> list = processDefinitionQuery.list();

        for (ProcessDefinition processDefinition : list) {
            map.put(processDefinition.getKey(),processDefinition);
        }

        return new ArrayList<ProcessDefinition>(map.values());
    }

    public void deleteProcessDefinitionByKey(String key) throws Exception{

        ProcessDefinitionQuery processDefinitionQuery = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery();
        List<ProcessDefinition> list=processDefinitionQuery.processDefinitionKey(key).list();
        for (ProcessDefinition processDefinition : list) {
            String deploymentId = processDefinition.getDeploymentId();

            flowWorkServiceImpl.getRepositoryService().deleteDeployment(deploymentId,true);
        }

    }
     //返回流程定义图片的文件流
    public InputStream getPngStream(String processDefinitionId)throws  Exception{
        ProcessDefinition procDef = flowWorkServiceImpl.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String diagramResourceName = procDef.getDiagramResourceName();
        InputStream imageStream = flowWorkServiceImpl.getRepositoryService().getResourceAsStream(
                procDef.getDeploymentId(), diagramResourceName);
        return imageStream;
    }
     //添加流程定义的文件，并部署
    public void deploymentProcessDefinition(InputStream inputStream) throws Exception {

        DeploymentBuilder deployment =flowWorkServiceImpl.getRepositoryService().createDeployment();
        deployment.addZipInputStream(new ZipInputStream(inputStream));
        Deployment deploy = deployment.deploy();
    }

    /**
     * 设置候选启动人、组
     * @param processDefinitionId
     * @param userArray
     * @param groupArray
     */
    public void setStartables(String processDefinitionId, String[] userArray, String[] groupArray) {

        // 1、清理现有的设置
        List<IdentityLink> links = flowWorkServiceImpl.getRepositoryService().getIdentityLinksForProcessDefinition(processDefinitionId);
        for (IdentityLink link : links) {
            if (StringUtils.isNotBlank(link.getUserId())) {
                flowWorkServiceImpl.getRepositoryService().deleteCandidateStarterUser(processDefinitionId, link.getUserId());
            }
            if (StringUtils.isNotBlank(link.getGroupId())) {
                flowWorkServiceImpl.getRepositoryService().deleteCandidateStarterGroup(processDefinitionId, link.getGroupId());
            }
        }

        // 2.1、循环添加候选人
        if (!ArrayUtils.isEmpty(userArray)) {
            for (String user : userArray) {
                flowWorkServiceImpl.getRepositoryService().addCandidateStarterUser(processDefinitionId, user);
            }
        }

        // 2.2、循环添加候选组
        if (!ArrayUtils.isEmpty(groupArray)) {
            for (String group : groupArray) {
                flowWorkServiceImpl.getRepositoryService().addCandidateStarterGroup(processDefinitionId, group);
            }
        }
    }
}
