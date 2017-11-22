package com.jthinking.deploy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * 增强的java.util.Properties，更适应Web项目
 * @author JiaBochao
 * @version 2017-11-22 10:04:19
 */
public class ClasspathProperties extends Properties {

    public ClasspathProperties() {
        super();
    }

    public ClasspathProperties(String path) throws IOException {
        super();
        load(path);
    }

    public void load(String path) throws IOException {
        if (path.startsWith("classpath:")) {
            path = path.substring("classpath:".length());
            URL resource = ClasspathProperties.class.getClassLoader().getResource(path);
            if (resource == null)
                throw new FileNotFoundException(path);
            super.load(resource.openStream());
        } else {
            super.load(new FileInputStream(path));
        }
    }
}
