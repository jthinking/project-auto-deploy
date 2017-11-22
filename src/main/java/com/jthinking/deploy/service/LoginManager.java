package com.jthinking.deploy.service;



import com.jthinking.deploy.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录业务接口
 * @author JiaBochao
 * @version 2017-11-22 15:32:20
 */
public interface LoginManager {

    User getLoginInfo(HttpServletRequest request);

    void checkUserInput(String email);

    User getUserInfo(String email);

    String sendDynamicPassword(String email);

    boolean auth(String password, String key);

}
