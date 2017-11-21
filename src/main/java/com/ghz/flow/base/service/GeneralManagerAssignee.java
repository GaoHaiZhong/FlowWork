package com.ghz.flow.base.service;

import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admi on 2017/9/5.
 * 总经理候选人的设置
 */

public class GeneralManagerAssignee implements TaskListener {

    @Resource
    UserMapper userMapper;


    public void notify(DelegateTask delegateTask) {
        List<User>    generalManagers= userMapper.selectByRolername(null, "总经理");
        for(User user:generalManagers){
            delegateTask.addCandidateUser(user.getLoginname());
        }
    }
}
