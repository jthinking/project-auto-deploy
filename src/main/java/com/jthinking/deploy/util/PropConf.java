package com.jthinking.deploy.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置管理工具
 * @author JiaBochao
 * @version 2017-9-20 11:00:54
 */
public class PropConf {

    private static Map<String, String> conf = new HashMap<String, String>();

    static {
        initConfFromFile();
    }

    /**
     * 获取一个不可变的配置信息Map
     * @return
     */
    public static Map<String, String> getConf() {
        return Collections.unmodifiableMap(conf);
    }

    /**
     * 获取指定配置项
     * @return
     */
    public static String getProp(String key) {
        return getConf().get(key);
    }

    /**
     * 从配置文件中初始化配置Map
     */
    private static void initConfFromFile() {
        try {
            ClasspathProperties properties = new ClasspathProperties("classpath:application.properties");
            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                conf.put((String)entry.getKey(), (String)entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
