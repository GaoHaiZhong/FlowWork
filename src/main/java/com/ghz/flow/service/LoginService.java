package com.ghz.flow.service;

import com.ghz.flow.base.pojo.Roler;
import com.ghz.flow.base.pojo.User;

import java.util.List;

/**
 * Created by admi on 2017/6/20.
 */
public interface LoginService {

    /**
     *根据用户的userName查询用户，password 验证数据库
     * @param user   用户的对象
     * @return
     */
    public User findUserByLoginNameAndPassword(User user);

    /**
     *
     * 根据登陆用户查找他领导
     * @return
     */
    public User findNextUserBythisId();

    /**
     * 根据用户的的主键查询用户
     * @param id
     * @return
     */
    public User selectById(String id);

    /***
     * 查询除我之外的所有用户
     * @param user
     * @return
     */
    List<User> selectAllUser(User user);

    List<Roler> selectAllRoler();
}
