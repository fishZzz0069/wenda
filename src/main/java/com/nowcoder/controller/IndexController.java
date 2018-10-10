package com.nowcoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

//@Controller
public class IndexController {

    @RequestMapping("/")
    @ResponseBody
    public String index(HttpSession httpSession){
        return "hello nowcodr" + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/vm"} , method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","vvvvv");
        return "home";
    }

    @RequestMapping(path = {"/redirect/{code}"} , method = {RequestMethod.GET})
    public RedirectView redirect(@PathVariable("code") int code , HttpSession httpSession){
        httpSession.setAttribute("msg", "jump from redirect");
        RedirectView red = new RedirectView("/",true);
        if (code == 301){
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return red;
    }
}
