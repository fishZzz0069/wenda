package com.nowcoder.service;

import org.springframework.stereotype.Service;

/**
 * Created by zsy on 2018/10/10.
 */
@Service
public class WendaService {
    public String getMessage(int userId) {
        return "Hello Message:" + String.valueOf(userId);
    }
}
