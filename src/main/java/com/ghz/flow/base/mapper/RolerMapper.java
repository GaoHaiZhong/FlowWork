package com.ghz.flow.base.mapper;

import com.ghz.flow.base.pojo.Roler;
import com.ghz.flow.base.pojo.RolerExample;
import com.ghz.flow.base.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RolerMapper {
    int countByExample(RolerExample example);

    int deleteByExample(RolerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Roler record);

    int insertSelective(Roler record);

    List<Roler> selectByExample(RolerExample example);

    Roler selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Roler record, @Param("example") RolerExample example);

    int updateByExample(@Param("record") Roler record, @Param("example") RolerExample example);

    int updateByPrimaryKeySelective(Roler record);

    int updateByPrimaryKey(Roler record);

    List<Roler> selectByUser(User user);

    List<Roler>  selectAllRoler();

    void setRolerByUserId(@Param("UserId")String UserId, @Param("groups")String[] groups);

    Roler selectById(Integer id);
}