package com.ghz.flow.service.impl;

import com.ghz.flow.base.mapper.RolerMapper;
import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.Roler;
import com.ghz.flow.base.pojo.User;
import com.ghz.flow.base.pojo.UserExample;
import com.ghz.flow.base.util.Request;
import com.ghz.flow.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

;


/**
 * Created by admi on 2017/6/20.
 */
@Service
public class LoginServiceImpl  implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RolerMapper rolerMapper;

    public User findUserByLoginNameAndPassword(User user) {
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginnameEqualTo(user.getLoginname());
        criteria.andPasswordEqualTo(user.getPassword());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size()!=0||users!=null){
             user= users.get(0);
            List<Roler> rolers = rolerMapper.selectByUser(user);
            if(rolers!=null){
              user.setRolerList(rolers);
            }
            return users.get(0);
        }
        return null;
    }

    public User findNextUserBythisId() {
        HttpServletRequest request = Request.getRequest();
        User  user =(User) request.getSession().getAttribute("user");
        System.out.println(user.getLoginname()+"---"+user.getPassword());
       /* user = userMapper.selectBythisId(user.getLoginname());*/
        return user;
    }

    public User  selectById(String id) {
        User user = userMapper.selectByPrimaryKey(Integer.parseInt(id));
        return user;
    }

    public List<User> selectAllUser(User user) {

        return   userMapper.selectAllUser(user);
    }

    public List<Roler> selectAllRoler() {

        List<Roler> rolers = rolerMapper.selectAllRoler();

        return rolers;
    }
}
