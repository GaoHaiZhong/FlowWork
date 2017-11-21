package com.ghz.flow.controller.base;

import com.ghz.flow.base.service.impl.FlowWorkServiceImpl;
import com.ghz.flow.service.FlowService;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.task.Attachment;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * Created by admi on 2017/9/3.
 */
@RequestMapping(value = "/attachment")
@Controller
public class AttachmentController {

    @Resource
    private FlowService flowService;
    @Resource
    private FlowWorkServiceImpl flowWorkService;

    /**
     * 上传文件类型的附件
     * @param taskId
     * @param processInstanceId
     * @param attachmentName
     * @param attachmentDescription
     * @param file
     * @return
     */
    @RequestMapping(value = "/addAttachmentFile/new/file")
    public String  addAttachmentFile(
            @RequestParam("taskId") String taskId,
            @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
            @RequestParam("attachmentName") String attachmentName,
            @RequestParam(value = "attachmentDescription", required = false) String attachmentDescription,
            @RequestParam("file") MultipartFile file, HttpServletRequest request){
        flowService.addAttachmenFile(file,taskId,processInstanceId,attachmentName,attachmentDescription);
        ProcessDefinitionEntity processDefinitionEntity = flowService.selectProcessDefinition(taskId);


        return "redirect:/"+processDefinitionEntity.getKey()+"/toTaskFormUI.action?taskId="+taskId;
    }


    @RequestMapping(value = "/addAttachmentUrl/new/url")
    public  String   addAttachmentUrl(@RequestParam("taskId") String taskId,
                                      @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
                                      @RequestParam("attachmentName") String attachmentName,
                                      @RequestParam(value = "attachmentDescription", required = false) String attachmentDescription,
                                      @RequestParam("url") String url,HttpServletRequest request){
        flowService.addAttachmentUrl(taskId,processInstanceId,attachmentName,attachmentDescription,url);
        ProcessDefinitionEntity processDefinitionEntity = flowService.selectProcessDefinition(taskId);
        return "redirect:/"+processDefinitionEntity.getKey()+"/toTaskFormUI.action?taskId="+taskId;
    }

    /**
     * 删除附件
     * @param attachmentId
     * @return
     */

    @RequestMapping(value = "delete/{attachmentId}")
    @ResponseBody
    public  String  deleteAttachment(@PathVariable("attachmentId")String attachmentId){
           flowService.deleteAttachmentById(attachmentId);

        return "true";
    }

    /**
     * 下载附件
     * @param attachmentId
     * @return
     */
    @RequestMapping(value = "/download/{attachmentId}")
    public  void   downloadFile(@PathVariable("attachmentId")String attachmentId,
                                  HttpServletResponse response)throws  Exception{
        Attachment attachment = flowWorkService.getTaskService().getAttachment(attachmentId);
        InputStream attachmentContent = flowWorkService.getTaskService().getAttachmentContent(attachmentId);

        String contentType = StringUtils.substringBefore(attachment.getType(), ";");
        //添加请求头类型
        response.addHeader("Content-Type", contentType + ";charset=UTF-8");
        String extensionFileName = StringUtils.substringAfter(attachment.getType(), ";");
        String fileName = attachment.getName() + "." + extensionFileName;
        //设置返回类型
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        //读写流
        IOUtils.copy(new BufferedInputStream(attachmentContent), response.getOutputStream());

    }



}
