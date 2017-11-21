package com.ghz.flow.service;


import com.ghz.flow.base.pojo.FormTemplate;

import java.util.List;

/**
 * Created by admi on 2017/6/23.
 */
public interface FromTemplateService {

    /**
     * 查询表表单模板列表
     * @return
     */
    public List<FormTemplate>  getFromTemplateList();

    /**
     * 添加表单模板
     * @param formTemplate
     */
    public void insertFormTemlate(FormTemplate formTemplate)throws Exception;

    /**
     *根据表单模板的Id删除表单模板
     * @param formTemlateId
     */
    public void deleteFormTemlateById(Integer formTemlateId)throws Exception;

    /**
     * g根据主键的id查询表单模板的实体
     * @param id    主键
     * @return
     */
    public FormTemplate selectFromTemplateById(Integer id);

    //根据流程定义key查询流程名
//    public List<String> selectProcessNameByKey(Stirng key);

    /**
     * 根据主键修改表单模板
     * @param id 主键
     */
    public void updateFormTemplateById(FormTemplate formTemplate);




}
