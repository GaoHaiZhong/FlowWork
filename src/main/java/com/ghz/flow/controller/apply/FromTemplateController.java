package com.ghz.flow.controller.apply;


import com.ghz.flow.base.pojo.FormTemplate;
import com.ghz.flow.base.util.upFile;
import com.ghz.flow.service.FromTemplateService;
import com.ghz.flow.service.IProcessDefinitionService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.ghz.flow.base.util.upFile.copyFile;


/**
 * Created by admi on 2017/6/22.
 */
@Controller
@RequestMapping(value = "/fromTemplate")
public class FromTemplateController {

    @Resource
    private FromTemplateService fromTemplateService;
    @Resource
    private IProcessDefinitionService iProcessDefinitionService;


    /**
     * 表单模板管理列表
     * @return
     */
    @RequestMapping(value = "/tolist")
    public  String toList(Model model){
        List<FormTemplate> fromTemplateList = fromTemplateService.getFromTemplateList();
        List<ProcessDefinition> processDefinitionByLastKey = iProcessDefinitionService.findProcessDefinitionByLastKey();

      if(fromTemplateList.size()>0&&processDefinitionByLastKey.size()>0){
          for(int i=0;i<fromTemplateList.size();i++){
              fromTemplateList.get(i).setProname(processDefinitionByLastKey.get(i).getName());
          }
      }

        model.addAttribute("list",fromTemplateList);
        //根据得到的KEY的查询流程名

        return "from_template/list";
    }

    /**
     * 表单模板添加页面
     * @return
     */
    @RequestMapping(value = "/toSaveUI")
    public String toSaveUI(Model model){
        //查询流程定义
        List<ProcessDefinition> processDefinitionByLastKey = iProcessDefinitionService.findProcessDefinitionByLastKey();
        model.addAttribute("namekey",processDefinitionByLastKey);

        return "from_template/saveUI";
    }

    /**
     * 提交表单模板股(包括文件的上上传)  和表单模板的修改
     * @return
     */
    @RequestMapping(value = "/submitFormTemplate")
    public String submitTemplate(FormTemplate formTemplate, MultipartFile resource)throws Exception{
        String path=null;
        //保存到磁盘
        //判断有没上传文件，路径是否为空、
        //如果为空的话，则为原来的文档路径
        //如果不为空的话。则要删除文档，和数据库中的路径
        System.out.println("resource.getName"+resource.getOriginalFilename());

        try {
           path= copyFile(resource,"formTemplate");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //保存到数据库中
        formTemplate.setDocpath(path);
        fromTemplateService.insertFormTemlate(formTemplate);
        return "redirect:/fromTemplate/tolist.action";
    }
     //删除时，相应的文件也被删除
    @RequestMapping(value = "/deleteById")
    public String   deleteById(Integer id)throws Exception{
        fromTemplateService.deleteFormTemlateById(id);
        System.out.println("------"+id);
        return "redirect:/fromTemplate/tolist.action";
    }

    //修改表单模板 要求：数据回显 ,
    //查询所有的key来选择
     @RequestMapping(value = "/toUpdateSaveUIBy")
    public String  toUpdateSaveUIBy(Integer id,Model model){
         List<String> list=new ArrayList<String>();
         FormTemplate formTemplate = fromTemplateService.selectFromTemplateById(id);
         List<ProcessDefinition> processDefinitionByLastKey = iProcessDefinitionService.findProcessDefinitionByLastKey();
         model.addAttribute("namekey",processDefinitionByLastKey);
         model.addAttribute("formTemplate",formTemplate);
         return "from_template/editUI";
    }

    /**
     *判断resource是否为空，
     *     如果不为空，这要删除文件了，和更改数据库
     *     如果为空，则要数据回显
     *
     */
    @RequestMapping(value = "/submitUpdateTemplate")
    public String submitUpdateTemplate(MultipartFile resource, FormTemplate formTemplate)throws Exception{
        System.out.println("-----submitUpdateTemplate------"+formTemplate.getId()+formTemplate.getPdkey()+formTemplate.getName());
        System.out.println("resource.getOriginalFilename()"+resource.getOriginalFilename());
        FormTemplate form = fromTemplateService.selectFromTemplateById(formTemplate.getId());
        form.setName(formTemplate.getName());
        form.setPdkey(formTemplate.getPdkey());
        if(resource.getOriginalFilename()!=null||!("").equals(resource.getOriginalFilename())){
            String docPath=form.getDocpath();
            File file=new File(docPath);
            file.delete();
           String path= copyFile(resource,"formTemplate");
            form.setDocpath(path);
        }
        //是修改不是添加，不然主键会重复
        fromTemplateService.updateFormTemplateById(form);
        return "redirect:/fromTemplate/tolist.action";
    }

    //文件的下载
    @RequestMapping(value = "/upload")
    public void  upLoad(Integer id, HttpServletResponse response, HttpServletRequest request) throws Exception {

        FormTemplate formTemplate = fromTemplateService.selectFromTemplateById(id);
        //文件名下载中文乱码

        String filename=formTemplate.getName()+"模板.doc";
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));

        upFile.upLoadFile(formTemplate.getDocpath(),response);

    }
}
