package com.ghz.flow.base.service;

import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.User;
import com.ghz.flow.base.util.Request;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by admi on 2017/9/5.
 */

/**
 *为部门审批设置候选人
 *可以在任务的结束后来进行任务的状态的审定
 */
@Component("departAssignee")
public class DepartAssignee implements /*JavaDelegate,*/TaskListener {

    @Autowired
    private UserMapper userMapper;



    public void notify(DelegateTask delegateTask) {

        String taskId = delegateTask.getId();
        System.out.println("-------------------DepartAssignee---------------------");

/*

         * SELECT u.* from user_link ul,`user` u WHERE ul.rolerId=u.id and ul.rolerId=
         * (select r.id from roler r where r.`key`="develop" and r.rolername="开发部门领导" )
*/

        User user =(User) Request.getRequest().getSession().getAttribute("user");
        //得到角色所对用所用用户
        for (int i=0;i<user.getRolerList().size();i++){
            List<User> users = userMapper.selectByRolername(user.getRolerList().get(0),"开发部门领导");
            //把用户设置参数中去
            for (User use:users){
                delegateTask.addCandidateUser(use.getLoginname());

          }
     }

   }


}
