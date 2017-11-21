package com.ghz.flow.service;

import com.ghz.flow.base.pojo.Application;

import java.util.List;

/**
 * Created by admi on 2017/7/21.
 */
public interface ApproveService {

    //查找下属的所有申请并进行审批
    public List<Application>  getListBysubordinates(Integer id);


//    public  void insertApproveInfo(Approveinfo approveinfo)throws Exception;


}
