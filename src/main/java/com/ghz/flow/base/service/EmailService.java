package com.ghz.flow.base.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admi on 2017/9/5.
 * 发送电子邮件
 */

public class EmailService implements JavaDelegate ,Serializable{
    //这里的电子邮件信息应该由数据库中获取，由于数据库没有设计所
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("to","ghz@myself.com");
        map.put("from", "admin@myself.com");
        map.put("subject","审核已通过，请及时查看");
        map.put("name","你好");
        map.put("mytime","PT2M");
        delegateExecution.setVariables(map);

    }
}
