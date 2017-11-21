package com.ghz.flow.controller.apply;


import com.ghz.flow.base.pojo.*;
import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.base.util.upFile;
import com.ghz.flow.service.ApplicationService;
import com.ghz.flow.service.FlowService;
import com.ghz.flow.service.FromTemplateService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller(value = "applicationController")
@RequestMapping(value = "/application")
public class ApplicationController {

    @Resource
    private FromTemplateService fromTemplateService;
    @Resource
    private ApplicationService applicationService;

    @Resource
    private FlowService flowService;

    @Resource
    private FlowWorkServiceImpl  flowWorkServiceImp;


    @RequestMapping(value = "/makeApplcation")
    public String  makeApplcation(Model model){
        List<FormTemplate> fromTemplateList = fromTemplateService.getFromTemplateList();
        model.addAttribute("fromTemplateList",fromTemplateList);
        return "/Flow_FormFlow/formTemplateList";
    }

    @RequestMapping(value = "/submitUI")
    public String  submitUI(String id,
                            @RequestParam(value = "pdkey",required = true) String pdkey,
                            HttpServletRequest request){
        request.setAttribute("id",id);
        request.setAttribute("pdkey",pdkey);
        System.out.println(id);
        return "/Flow_FormFlow/submitUI";
    }
    //申请文件的上传,进行页面的跳转，查看我的所有申请
    //并启动流程实例，并提交，下一个任务的完成人   leaveWork
    @RequestMapping(value = "/upFile")
    public  String upFile(MultipartFile resource,
                          @RequestParam(value = "templateId" ,required = true)Integer templateId,
                          Leave leave,
                          HttpServletRequest request)throws  Exception{

        System.out.println(templateId+"----------------------------");
       //不为空时
      if(!resource.isEmpty()) {
              try {

                  FormTemplate formTemplate = fromTemplateService.selectFromTemplateById(templateId);
                  String path = upFile.copyFile(resource, "application");
                  Date date = new Date();
                  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  String str_date = simpleDateFormat.format(date);

                  User user = (User) request.getSession().getAttribute("user");
                  //将申请的表单内容填写到数据库中
                  String title = formTemplate.getName() + "_" +user.getLoginname() + "_" + str_date;
                  Application application = new Application();
                  application.setApplicant(user.getId());
                  application.setApplydate(simpleDateFormat.parse(str_date));
                  application.setTitle(title);
                  application.setDocfilepath(path);
                  application.setStatus(Application.STATUS_APPROVING);
                  application.setTemplate(formTemplate.getId());
                  application.setRejecttime(1);
                  //放入对象
                  application.setFormTemplate(formTemplate);
                  application.setUser(user);
                  //存入数据库
                  applicationService.insertAndgetId(application);

                  String pdkey = formTemplate.getPdkey();//开启最新的流程实例
                  System.out.println(application.getId()+"==================");
                  ProcessInstance processInstance = flowService.startProcessInstance(leave,pdkey, application.getId());
                  request.setAttribute("processInstanceId",processInstance.getProcessInstanceId());
              } catch (IOException e) {
                  e.printStackTrace();
              }

          }

        return "forward:/application/getApplicationList.action";
    }

    /**
     * 根据表单开启流程实例
     * @param leave
     * @param request
     * @return
     */
    @RequestMapping(value = "/startProcess")
    public String startProcess(Leave leave,HttpServletRequest request){


        return "";
    }

         //查看我的所有申请申请
         //用户
    //这里需要琢磨琢磨

    /**
     * 这里请假要看身份，普通员工有普通股员工的申请方式，
     * 经理，总经理等上级领导的申请方式
     * @param request
     * @return
     */
    @RequestMapping(value = "/getApplicationList")
        public ModelAndView  getApplicationList(@RequestParam(value = "status",required = false)String status,
                                                @RequestParam(value = "pageNum",required = false)Integer pageNum,
                HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("/Flow_FormFlow_Old/mySubmittedList");

        /*  User user=(User) request.getSession().getAttribute("user");
            List<Application> list = applicationService.selectApplicationList(user.getId());
            request.setAttribute("list", list);*/
          //查询登陆用户的所有申请
        Map<String,List<FlowBean>> hisTaskByloginName = flowService.getHisTaskByloginName();
        List<FlowBean> applicationList = hisTaskByloginName.get("applicationList");
        List<FlowBean> rejectApplicationList = hisTaskByloginName.get("rejectApplicationList");
        modelAndView.addObject("list", applicationList);
        modelAndView.addObject("unlist", rejectApplicationList);

        //
        if(pageNum==null){
             pageNum=0;
        }
        Map<String, Object> applicationList1 = flowService.getApplicationList(status,pageNum);
        modelAndView.addObject("pageInfo",applicationList1.get("pageInfo"));
        modelAndView.addObject("pageList",applicationList1.get("pageList"));
        modelAndView.addObject("status",status);



        //


          return modelAndView;

        }


    /**
     * 驳回后，跳转到重新申请页面你
     * @return
     */
    @RequestMapping(value = "/updateApplyUI")
       public String updateApplyUI(@RequestParam(value = "processInstanceId",required =true)String processInstanceId,
                                HttpServletRequest request
                               ){
        String applicationId = flowService.getBusinessKeyByProcessInstance(processInstanceId);
        request.setAttribute("processInstanceId",processInstanceId);
        request.setAttribute("applicationId",applicationId);

        return "/Flow_FormFlow/editAndResubmitUI";
       }
        //提交修改之后的申请，并提交,这里需要修改修改
       @RequestMapping(value = "/againApply")
       public  String  againApply(MultipartFile resource,
                                  @RequestParam(value = "processInstanceId",required = true) String processInstanceId,
                                  HttpServletRequest request){
           if(!resource.isEmpty()){
               String s_applicationId = flowService.getBusinessKeyByProcessInstance(processInstanceId);
               Application application = applicationService.selectById(Integer.parseInt(s_applicationId),null);
                   File file=new File(application.getDocfilepath());
               file.delete();
               application.setDocfilepath(resource.getOriginalFilename());
               String taskId = flowService.getTaskId(processInstanceId,"");
               flowService.completeTask(taskId,null,null);

           }

           return "forward:/application/getApplicationList.action";



       }

       //根据流程定义的pdkey来选择表单
       @RequestMapping(value = "/toFormUI")
        public  String toFormUI(@RequestParam(value = "pdkey",required = true)String formType,
                                @RequestParam(value = "id",required = true)Integer templateId,
                                HttpServletRequest request){
          request.setAttribute("templateId",templateId);
           return  "/form/"+formType+"/start";
        }
}
