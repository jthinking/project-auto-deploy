package com.jthinking.deploy.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 * @author JiaBochao
 * @version 2017-11-22 15:43:15
 */
@Controller
public class PageController {

    @RequestMapping({"", "/", "/index"})
    public String index() {
        return "redirect:/project/deployPage";
    }
}
