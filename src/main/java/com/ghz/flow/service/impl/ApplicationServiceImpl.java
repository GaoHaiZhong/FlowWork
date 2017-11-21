package com.ghz.flow.service.impl;


import com.ghz.flow.base.mapper.ApplicationMapper;
import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.Application;
import com.ghz.flow.base.pojo.ApplicationExample;
import com.ghz.flow.base.pojo.User;
import com.ghz.flow.service.ApplicationService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admi on 2017/7/13.
 */
@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    private ApplicationMapper applicationMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ProcessEngine processEngine;


    public Integer insertAndgetId(Application application) throws Exception {
        Integer appl = applicationMapper.insertAndgetId(application);
        return appl;
    }

    //如果涉及到分页，该如何处理
    public List<Application> selectApplicationList(Integer id) {
        ApplicationExample applicationExample = new ApplicationExample();
        applicationExample.setOrderByClause("applyDate");
        ApplicationExample.Criteria criteria = applicationExample.createCriteria();
        criteria.andApplicantEqualTo(id);

        List<Application> list = applicationMapper.selectByExample(applicationExample);

        return list;
    }

    public List<Application> getListBysubordinates(Integer id) {

        List<Application> list = applicationMapper.selectBysubordinates(id);
        for (Application application : list) {
            User user = userMapper.selectByPrimaryKey(application.getApplicant());
            application.setUser(user);

        }
        return list;
    }

    public Application selectById(Integer id, String status) {
        /*ApplicationExample  applicationExample=new ApplicationExample();
        ApplicationExample.Criteria criteria = applicationExample.createCriteria();
        if(id!=null){
            criteria.andIdEqualTo(id);

        }
        User user =(User)Request.getRequest().getSession().getAttribute("user");
        criteria.andApplicantEqualTo(user.getId());
        if(StringUtils.isNoneBlank(status)){
           criteria.andStatusEqualTo(status);
        }
        List<Application> applications = applicationMapper.selectByExample(applicationExample);
        if(applications.size()==1){
           return applications.get(0);
        }
        return  null;*/

        Application application = applicationMapper.selectByPrimaryKey(id);
        if (application == null) {
            try {
                throw new Exception("application 查询结果为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return application;
    }

    public ProcessDefinition getProcessDefinition() {
        RuntimeService runtimeService = processEngine.getRuntimeService();

        return null;
    }

    public void updateApplication(Application application) {
        applicationMapper.updateByPrimaryKey(application);
    }

    public List<Application> selectApplicationByPage(int[] init1, String status, Integer userId) {
        List<Application> applications = applicationMapper.selectApplicatByPage(init1, status, userId);
        return applications;
    }


}
