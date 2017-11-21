package com.ghz.flow.controller.identity;

import com.ghz.flow.base.mapper.RolerMapper;
import com.ghz.flow.base.mapper.UserMapper;
import com.ghz.flow.base.pojo.Roler;
import com.ghz.flow.base.pojo.User;
import com.ghz.flow.base.util.Page;
import com.ghz.flow.base.util.PageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admi on 2017/9/17.
 * 组管理界面
 */
@Controller
@RequestMapping("identity")
public class IdentityController {

    @Resource
    private RolerMapper rolerMapper;
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/toRollerList")
    public ModelAndView toRollerList(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("/processDefinitionView/group-list");
        Page<Roler>  page=new Page<Roler>(8);
        int[] init = PageUtil.init(page, request);
        List<Roler> rolers = rolerMapper.selectAllRoler();
        int i = rolerMapper.countByExample(null);

        page.setResult(rolers);
        page.setTotalCount((long)i);
        modelAndView.addObject("page",page);
        return modelAndView;
    }



    @RequestMapping("/toUserList")
    public ModelAndView toUserList(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("/processDefinitionView/user-list");
        Page<User>  page=new Page<User>(PageUtil.PAGE_SIZE);
        int[] init = PageUtil.init(page, request);

        List<User> users = userMapper.selectAllUser(null);
        int i = userMapper.countByExample(null);
        Map<Integer,List<Roler>> rolerMap=new HashMap<Integer, List<Roler>>();
        //根据用户名查询所拥有的角色
        for(User user:users){
            List<Roler> rolers = rolerMapper.selectByUser(user);
             rolerMap.put(user.getId(),rolers);
        }
        List<Roler> rolers = rolerMapper.selectAllRoler();


        page.setResult(users);
        page.setTotalCount((long)i);
        modelAndView.addObject("page",page);
        modelAndView.addObject("groupOfUserMap",rolerMap);
        modelAndView.addObject("allGroup",rolers);

        return modelAndView;
    }


    @RequestMapping("/deleteUser/{userId}")
    public  String  deleteUser(@PathVariable("userId")String userId){
        int i1 = Integer.parseInt(userId);
        userMapper.deleteByPrimaryKey(i1);

        return "redirect:/identity/toUserList.action";
    }


    @RequestMapping("/saveUser")
    public  String saveUser(User  user){
        //查询是否存在Id
        User dateBaseUser = userMapper.selectByPrimaryKey(user.getId());
        if(dateBaseUser!=null){
            userMapper.updateByPrimaryKey(user);
        }else {
            user.setId(null);//这里由于mysql的自增长，所以设置为null；不然会报错
            int insert = userMapper.insert(user);
        }
        return "redirect:/identity/toUserList.action";

    }

    @RequestMapping("/deleteRoler/{rolerId}")
    public String deleteRoler(@PathVariable("rolerId")String rolerId){
        int i1 = Integer.parseInt(rolerId);
      rolerMapper.deleteByPrimaryKey(i1);
        return "redirect:/identity/toRollerList.action";
    }

    @RequestMapping("/setRoler")
    public String  setRoler(@RequestParam("group")String[] groups,
                            @RequestParam(value = "userId",required = true)String userId){
       //某个用户添加角色
        rolerMapper.setRolerByUserId(userId,groups);

        return "redirect:/identity/toUserList.action";
    }



    @RequestMapping("/saveRoler")
     public  String saveRoler(Roler roler){
        Integer id = roler.getId();
        Roler roler2 = rolerMapper.selectByPrimaryKey(roler.getId());

        if(roler2!=null){
            rolerMapper.updateByPrimaryKey(roler);
        }else{
            roler.setId(null);
            rolerMapper.insert(roler);

        }
        return "redirect:/identity/toRollerList.action";

     }
}
