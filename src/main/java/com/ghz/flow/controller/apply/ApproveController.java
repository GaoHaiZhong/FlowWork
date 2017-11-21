package com.ghz.flow.controller.apply;

import com.ghz.flow.base.pojo.FlowBean;
import com.ghz.flow.base.util.Page;
import com.ghz.flow.base.util.PageUtil;
import com.ghz.flow.service.ApplicationService;
import com.ghz.flow.service.ApproveService;
import com.ghz.flow.service.FlowService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by admi on 2017/8/30.
 */
@Controller
@RequestMapping(value = "/approve")
public class ApproveController
{

    @Resource
    private ApproveService approveService;
    @Resource
    private ApplicationService applicationService;
    @Resource
    private FlowService flowService;

    //待我审批
    @RequestMapping(value = "/waitApprove")
    public ModelAndView waitApprove(HttpServletRequest request){
        String viewName="/Flow_FormFlow/myTaskList";
        ModelAndView modelAndView=new ModelAndView(viewName);
        //要先知道是那个用户申请的，提交到那个上司（动态）
        //如果有多级上司，可以退回，和审批/
        //查询我的下属的申请，并审批是否同意
        Page<FlowBean> currentTaskPage=new Page<FlowBean>(PageUtil.PAGE_SIZE);
        int[] init = PageUtil.init(currentTaskPage, request);
        List<FlowBean> currentTask = flowService.getCurrentTask();



        Page<FlowBean>  historicPage=new Page<FlowBean>();
        List<FlowBean> historic = flowService.getHistoric();
        modelAndView.addObject("list",currentTask);
        modelAndView.addObject("hislist",historic);



        return modelAndView;
    }
}
