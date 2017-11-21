package com.ghz.flow.base.service;

import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.User;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admi on 2017/9/5.
 */

public class HelpAssignee implements TaskListener{

   @Resource
    UserMapper userMapper;

    public void notify(DelegateTask delegateTask) {
        List<User> Mishu = userMapper.selectByRolername(null, "秘书");
        for (User user:Mishu){
            delegateTask.addCandidateUser(user.getLoginname());
        }
    }
}
