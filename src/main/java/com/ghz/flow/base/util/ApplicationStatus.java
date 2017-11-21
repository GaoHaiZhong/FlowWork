package com.ghz.flow.base.util;

import com.ghz.flow.base.pojo.Application;

import java.util.HashMap;
import java.util.Map;

/**
 * 申请状态
 * Created by admi on 2017/10/14.
 */
public class ApplicationStatus {

    //根据对应的的Key获得属性
    public static String getApplicationStatusByApprove(String approval) {
        Map<String, String> applicationStatus = new HashMap<String, String>();
        //通过
        applicationStatus.put("1", Application.STATUS_APPROVED);
        //驳回
        applicationStatus.put("3", Application.STATUS_APPROVING);
        //不同意
        applicationStatus.put("2", Application.STATUS_UNAPPROVED);

        return applicationStatus.get(approval);

    }

}
