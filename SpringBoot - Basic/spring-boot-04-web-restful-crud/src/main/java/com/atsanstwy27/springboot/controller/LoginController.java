package com.atkjs927.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    /*
    @DeleteMapping
    @PutMapping
    @GetMapping
     */

    //@RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @PostMapping(value = "/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Map<String,Object> map, HttpSession session){
        if(!StringUtils.isEmpty(username) && "123456".equals(password)){
            // Login Success
            session.setAttribute("loginUser",username);
            return "redirect:/main.html";
        }else{
            // Login Failure
            map.put("msg","用戶名密碼錯誤");
            return  "login";
        }

    }
}
