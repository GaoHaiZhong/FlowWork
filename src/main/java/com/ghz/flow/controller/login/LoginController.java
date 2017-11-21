package com.ghz.flow.controller.login;

import com.ghz.flow.base.pojo.User;
import com.ghz.flow.service.LoginService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by admi on 2017/8/27.
 */
@Controller
public class LoginController {

    @Resource
    private LoginService loginService;

    /**
     * @param user
     * @param request
     * @return
     * @ModelAttribute进行数据的回显（如果提交失败的话，就会进行数据的回显）
     * @Validated表示在对items参数绑定时进行校验，校验信息写入BindingResult中， 在要校验的pojo后边添加BingdingResult，
     * 一个BindingResult对应一个pojo，且BingdingResult放在pojo的后边
     * 这里对进行了验证
     */

    @RequestMapping(value = "/login")
    public String login(@Valid User user, BindingResult result,
                        HttpServletRequest request) {
        //对user参数进行验证
        if (result.hasErrors()) {
            //得到异常信息不i人
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                System.out.println(objectError.getCode());
                System.out.println(objectError.getDefaultMessage());
                return "index";
            }

        }

        user = loginService.findUserByLoginNameAndPassword(user);
        System.out.println("经过了login" + user.getLoginname() + "======" + user.getPassword());
        request.getSession().setAttribute("user", user);
        if (user != null) {
            return "home/index";
        }
        return null;

    }

    @RequestMapping(value = "/loginout")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "logout";

    }
}
