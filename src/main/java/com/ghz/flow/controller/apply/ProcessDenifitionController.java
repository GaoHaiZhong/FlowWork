package com.ghz.flow.controller.apply;

import com.ghz.flow.service.IProcessDefinitionService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by admi on 2017/8/27.
 * 流程申请股管理
 */
@Controller
@RequestMapping(value = "/processDefinitionController")
public class ProcessDenifitionController {


    @Resource
    private IProcessDefinitionService iProcessDefinitionService;

    @RequestMapping(value = "/toList")
    public  String toList(Model model){
        System.out.println("经过了toList页面");
        List<ProcessDefinition> list = iProcessDefinitionService.findProcessDefinitionByLastKey();
        for (ProcessDefinition processDefinition : list) {
            System.out.println(processDefinition.getName()+""+processDefinition.getKey());
        }
        model.addAttribute("list",list);
        return "pd/list";
    }

    @RequestMapping(value = "/delprodef")
    public String  deleteProcessDefinition(String key){
        if(key==null||("").equals(key)){
            try {
                throw new Exception("key 为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            iProcessDefinitionService.deleteProcessDefinitionByKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/processDefinitionController/toList.action";
    }

    /**
     *
     * @param processDefinitionId
     * @param response
     */
    @RequestMapping(value = "/seePng")
    public void seePng(@RequestParam(value = "processDefinitionId",required = true) String processDefinitionId,
                       HttpServletResponse response){
        response.setContentType("image/jpeg");
        InputStream stream=null;
        ServletOutputStream outputStream=null;
        if (processDefinitionId==null||("").equals(processDefinitionId)){
            try {
                throw new Exception("processDefinitionId为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            stream= iProcessDefinitionService.getPngStream(processDefinitionId);
            outputStream = response.getOutputStream();
            byte[] bytes=new byte[2048];
            int a=0;
            while ((a=stream.read(bytes))!=-1){
                outputStream.write(bytes,0,a);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件的上传  存储到数据库中
     * @param resource  文件
     * @return
     */
    @RequestMapping(value = "/deploydef")
    public  String  deploymentProcessDefinition(MultipartFile resource){
        InputStream inputStream=null;
        String originalFilename = resource.getOriginalFilename();
        if(!originalFilename.contains(".zip")){
            try {
                throw new Exception("后缀出现错误");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            inputStream = resource.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            iProcessDefinitionService.deploymentProcessDefinition(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "redirect:/processDefinitionController/toList.action";
    }




}
