package com.ghz.flow.base.service;

import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import javax.annotation.Resource;
import java.util.List;

/**
 * 为人事审批设置候选人
 */



public class HrAssignee implements TaskListener{

    @Resource
    UserMapper userMapper;


    public void notify(DelegateTask delegateTask) {

        List<User> HrPerson = userMapper.selectByRolername(null, "人事部门");
        for(User user:HrPerson){
            delegateTask.addCandidateUser(user.getLoginname());
        }
    }


}
