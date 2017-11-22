package com.jthinking.deploy.util;


import com.jthinking.deploy.pojo.User;

/**
 * 实现线程安全
 */
public class UserThreadLocal {

    private static final ThreadLocal<User> USER = new ThreadLocal<User>();

    public static void set(User user) {
        USER.set(user);
    }

    public static User get() {
        return USER.get();
    }

    public static String getUsername() {
        if (null != USER) {
            if (null != USER.get()) {
                return USER.get().getUsername();
            }
        }
        return null;
    }
}

