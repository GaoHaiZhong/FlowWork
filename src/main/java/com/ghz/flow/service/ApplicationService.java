package com.ghz.flow.service;

import com.ghz.flow.base.pojo.Application;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.List;

/**
 * Created by admi on 2017/7/13.
 */
public interface ApplicationService {

    public  Integer insertAndgetId(Application application)throws  Exception;

    //需要份分页吗？(这里美考虑分页的情况)某个用户的所有申请列表
    public List<Application>  selectApplicationList(Integer id);

    //查找下属的所有申请并进行审批
    public List<Application>  getListBysubordinates(Integer id);

    //根据id查询所对应的申请
    public Application selectById(Integer id,String staut);

    /**
     * 获得流程的实例
     * @return  返回一个流程的实例
     */
    public ProcessDefinition getProcessDefinition();

    void updateApplication(Application application);

    /**
     * 分页查询
     * @param init1
     */
    List<Application> selectApplicationByPage( int[] init1,String status,Integer userId);
}
