package com.ghz.flow.service.impl;


import com.ghz.flow.base.mapper.FormTemplateMapper;
import com.ghz.flow.base.pojo.FormTemplate;
import com.ghz.flow.service.FromTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by admi on 2017/6/23.
 */
@Service
public class FromTemplateServiceImpl implements FromTemplateService {

    @Resource
    private FormTemplateMapper formTemplateMapper;



    public List<FormTemplate> getFromTemplateList() {
        List<FormTemplate> formTemplates = formTemplateMapper.selectByExample(null);
        return formTemplates;
    }

    public void insertFormTemlate(FormTemplate formTemplate) throws Exception{
        formTemplateMapper.insert(formTemplate);

    }

    public void deleteFormTemlateById(Integer formTemlateId) throws Exception{
      formTemplateMapper.deleteByPrimaryKey(formTemlateId);
    }

    public FormTemplate selectFromTemplateById(Integer id) {

        FormTemplate formTemplate = formTemplateMapper.selectByPrimaryKey(id);
        if(formTemplate==null){
            try {
                throw new Exception("查找不到该实体，或者");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return formTemplate;
    }

    public void updateFormTemplateById(FormTemplate formTemplate) {
        int i = formTemplateMapper.updateByPrimaryKeySelective(formTemplate);
        System.out.println(i);

    }


}
