package com.jthinking.deploy.service;


import com.jthinking.deploy.pojo.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录业务Session实现
 * @author JiaBochao
 * @version 2017-11-22 15:33:44
 */
@Service("sessionLoginManager")
public class SessionLoginManager extends AbstractLoginManager {

    @Override
    public User getLoginInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return null;
        } else {
            return user;
        }
    }


}
