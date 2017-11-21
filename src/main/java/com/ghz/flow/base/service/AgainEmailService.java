package com.ghz.flow.base.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**@author admi
 * Created by admi on 2017/9/14.
 */
public class AgainEmailService implements JavaDelegate,Serializable {


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("to","ghz@myself.com");
        map.put("from", "admin@myself.com");
        map.put("subject","以通过，请消假");





        delegateExecution.setVariables(map);
    }
}
