package com.ghz.flow.service.impl;


import com.ghz.flow.base.pojo.Application;
import com.ghz.flow.service.ApproveService;
import org.activiti.engine.ProcessEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ApproveServiceImpl implements ApproveService {


    /* @Resource
     private ApproveinfoMapper approveinfoMapper;*/
    //注解，来获得流程引擎实例
    @Resource
    private ProcessEngine processEngine;


    public List<Application> getListBysubordinates(Integer id) {


        return null;
    }

  /*  public void insertApproveInfo(Approveinfo approveinfo) throws Exception {
        approveinfoMapper.insertSelective(approveinfo);
    }
*/
    //开启一个流程实例


}
