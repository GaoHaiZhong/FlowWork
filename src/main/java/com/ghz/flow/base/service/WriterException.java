package com.ghz.flow.base.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * Created by admi on 2017/9/5.
 * 记录异常
 */
@Component("writerException")
public class WriterException implements JavaDelegate {
    public void execute(DelegateExecution delegateExecution) throws Exception {

    }
}
