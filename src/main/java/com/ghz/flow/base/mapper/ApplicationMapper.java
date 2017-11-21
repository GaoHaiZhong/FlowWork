package com.ghz.flow.base.mapper;

import com.ghz.flow.base.pojo.Application;
import com.ghz.flow.base.pojo.ApplicationExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationMapper {
    int countByExample(ApplicationExample example);

    int deleteByExample(ApplicationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Application record);

    int insertSelective(Application record);

    List<Application> selectByExample(ApplicationExample example);

    Application selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Application record, @Param("example") ApplicationExample example);

    int updateByExample(@Param("record") Application record, @Param("example") ApplicationExample example);

    int updateByPrimaryKeySelective(Application record);

    int updateByPrimaryKey(Application record);

    public List<Application>  selectBysubordinates(Integer id);

    public Integer insertAndgetId(Application application);

    public  List<Application>  selectApplicatByPage(@Param("init") int[] init, @Param("status") String status, @Param("userName")Integer userId);
}