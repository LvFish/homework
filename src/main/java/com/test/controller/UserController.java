package com.test.controller;

import com.test.dao.UserDao;
import com.test.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by liujiawang on 2019/5/16.
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @RequestMapping(value="/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            System.out.println(username+" "+password);
            List<UserEntity> users = userDao.getByUserName(username);
            UserEntity user = users.get(0);
            if (password.equals(user.getPassword())) {
                session.setAttribute("userId",user.getUserId());
                if(user.getIsTeacher()==0)  //学生返回index，教师返回教师的index
                    return "index";
                else
                    return "teacherIndex";
            } else {
                session.setAttribute("message","密码错误");
                return "redirect:/login";
            }
        }catch (Exception e){
            session.setAttribute("message","该账号不存在");
            e.printStackTrace();
            return "redirect:/login";
        }
    }

}
