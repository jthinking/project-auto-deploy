package com.jthinking.deploy.web.controller;

import com.jthinking.deploy.pojo.User;
import com.jthinking.deploy.service.LoginManager;
import com.jthinking.deploy.web.exception.UserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 管理员登录管理
 * 通过动态密码登录，发送6位密码到邮箱
 * @author JiaBochao
 * @version 2017-9-26 08:32:40
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    private LoginManager sessionLoginManager;

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(HttpServletRequest request, Model model, String email, String service) {
        try {
            HttpSession session = request.getSession();
            if (request.getMethod().equals("GET")) {
                model.addAttribute("service", service);
                return "login";
            }
            if (request.getMethod().equals("POST")) {
                sessionLoginManager.checkUserInput(email);
                User user = sessionLoginManager.getUserInfo(email);
                String key = sessionLoginManager.sendDynamicPassword(email);
                session.setAttribute("email", user.getEmail());
                session.setAttribute("key", key);
                model.addAttribute("service", service);
                return "verify";
            }
            return "login";
        } catch (UserException e) {
            model.addAttribute("msg", e.getMessage());
            return "login";
        } catch (Exception e) {
            model.addAttribute("msg", "系统异常");
            return "login";
        }
    }

    @RequestMapping(value = "/verify", method = {RequestMethod.POST})
    public String verify(HttpServletRequest request, Model model, String password, String service) {
        try {
            HttpSession session = request.getSession();
            String key = (String) session.getAttribute("key");
            String email = (String) session.getAttribute("email");
            boolean result = sessionLoginManager.auth(password, key);
            if (result) {
                User user = sessionLoginManager.getUserInfo(email);
                session.setAttribute("user", user);
                if (service != null && !service.equals("")) {
                    return "redirect:" + service;
                } else {
                    return "redirect:/project/deployPage";
                }
            } else {
                session.setAttribute("email", null);
                session.setAttribute("key", null);
                return "redirect:/user/login";
            }
        } catch (UserException e) {
            model.addAttribute("msg", e.getMessage());
            return "verify";
        } catch (Exception e) {
            model.addAttribute("msg", "系统异常");
            return "verify";
        }
    }

    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("key", null);
        session.setAttribute("email", null);
        session.setAttribute("user", null);
        return "redirect:/user/login";
    }


}
