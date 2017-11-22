package com.jthinking.deploy.web.listener;


import com.jthinking.deploy.util.ClasspathProperties;
import com.jthinking.deploy.util.PropConf;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * 设置应用全局变量
 * @author JiaBochao
 * @version 2017-11-22 10:09:11
 */
@WebListener
public class GlobalVariableServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<String, String> conf = PropConf.getConf();
        for (Map.Entry<String, String> entry : conf.entrySet()) {
            servletContextEvent.getServletContext().setAttribute(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
