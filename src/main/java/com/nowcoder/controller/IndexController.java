package com.nowcoder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type", defaultValue = "1") int type,
                          @RequestParam(value = "key", required = false) String key) {
        return String.format("Profile Page of %s / %d, t:%d k: %s", groupId, userId, type, key);
    }
}
