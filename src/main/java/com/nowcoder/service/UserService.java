package com.nowcoder.service;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User getUser(int id){
        return userDAO.selectById(id);
    }

    public Map<String ,Object> register(String username,String password){
        Map<String,Object > map = new HashMap<>();

        if (StringUtils.isBlank(username)) {
            map.put("msg", "username can't be blank");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "password can't be blank");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user!=null){
            map.put("msg","the username already exists");
            return map;
        }

        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        String ticket = addLoginTicket(user.getId());
        //注册成功，下发一个该用户的ticket
        map.put("ticket",ticket);

        return map;

    }

    public Map<String ,Object> login(String username,String password){
        Map<String,Object > map = new HashMap<>();

        if (StringUtils.isBlank(username)) {
            map.put("msg", "username can't be blank");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "password can't be blank");
            return map;
        }

        User user = userDAO.selectByName(username);
        if (user == null){
            map.put("msg","the username doesnt exist");
            return map;
        }

        if (WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())){
            map.put("msg","wrong password");

        }

        String ticket = addLoginTicket(user.getId());
        //登陆成功，将该用户的ticket表存入map中
        map.put("ticket",ticket);

        return map;

    }


    //生成对应该用户ID的ticket并存入数据库中，返回tickt值
    public String addLoginTicket(int userId){
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }



}
