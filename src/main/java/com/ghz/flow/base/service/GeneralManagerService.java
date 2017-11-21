package com.ghz.flow.base.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by admi on 2017/9/5.
 * 判断是否需要总经理审批
 */
public class GeneralManagerService implements JavaDelegate,Serializable
{


    public void execute(DelegateExecution delegateExecution) throws Exception {


        Map<String, Object> variables = delegateExecution.getVariables();
        Date startDate = (Date) variables.get("startDate");
        Date endDate = (Date) variables.get("endDate");

        long startTime = startDate.getTime();
        long endTime = endDate.getTime();
        //两天多少秒
        long time=3*24*60*60*1000;
        double days=((endTime-startTime)-time)/(24*60*60*1000);
        delegateExecution.setVariable("days",days);

        //设置定时器的出发时间(为了方便测试吗，我这里启用10s)


        //比较两个时间差



    }
}
