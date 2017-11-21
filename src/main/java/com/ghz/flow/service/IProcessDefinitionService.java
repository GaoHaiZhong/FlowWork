package com.ghz.flow.service;



import org.activiti.engine.repository.ProcessDefinition;

import java.io.InputStream;
import java.util.List;

/**
 * Created by admi on 2017/6/21.
 */
public interface IProcessDefinitionService {
    /**
     * 根据key获得最新的流程定义
     * @return  流程定义的集合
     */
    public List<ProcessDefinition> findProcessDefinitionByLastKey();

    /**
     * 根据key删除流程定义
     * @param key
     */
    public void  deleteProcessDefinitionByKey(String key)throws Exception;

    /**
     *根据流程定义的id查找流程定义的png的读取流
     * @param  processDefinitionId 流程定义的id
     * @return
     */
    public InputStream getPngStream(String processDefinitionId)throws Exception;

    /**
     * 部署流程定义
     * @param zip
     * @throws Exception
     */
    public void deploymentProcessDefinition(InputStream inputStream)throws Exception;


    public void setStartables(String processDefinitionId, String[] userArray, String[] groupArray);

}
