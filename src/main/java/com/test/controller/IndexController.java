package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by liujiawang on 2019/5/16.
 */

@Controller
@RequestMapping("")
public class IndexController {




    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "login";
    }


    @RequestMapping(value="/login",method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    @RequestMapping(value="/managerLogin",method = RequestMethod.GET)
    public String managerLogin(){
        return "managerLogin";
    }


}