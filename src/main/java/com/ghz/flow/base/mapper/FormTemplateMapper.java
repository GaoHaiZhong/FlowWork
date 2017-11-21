package com.ghz.flow.base.mapper;

import com.ghz.flow.base.pojo.FormTemplate;
import com.ghz.flow.base.pojo.FormTemplateExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormTemplateMapper {
    int countByExample(FormTemplateExample example);

    int deleteByExample(FormTemplateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FormTemplate record);

    int insertSelective(FormTemplate record);

    List<FormTemplate> selectByExample(FormTemplateExample example);

    FormTemplate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FormTemplate record, @Param("example") FormTemplateExample example);

    int updateByExample(@Param("record") FormTemplate record, @Param("example") FormTemplateExample example);

    int updateByPrimaryKeySelective(FormTemplate record);

    int updateByPrimaryKey(FormTemplate record);
}