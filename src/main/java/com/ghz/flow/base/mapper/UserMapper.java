package com.ghz.flow.base.mapper;

import com.ghz.flow.base.pojo.Roler;
import com.ghz.flow.base.pojo.User;
import com.ghz.flow.base.pojo.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    //根据用户的id查询角色
    List<User>  selectByRolername(@Param("roler") Roler roler,@Param("rolername") String rolername);


    List<User> selectAllUser(User user);
}