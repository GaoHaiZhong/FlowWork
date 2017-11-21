package com.ghz.flow.base.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

/**
 * Created by admi on 2017/9/15.
 */
public class MessageCatch implements ExecutionListener {
    public void notify(DelegateExecution delegateExecution) throws Exception {

        delegateExecution.getEngineServices().getRuntimeService()
                .messageEventReceived("help_message",null);

        System.out.println("经过了");
    }
}
