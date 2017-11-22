package com.jthinking.deploy.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Shell脚本工具类
 * @author JiaBochao
 * @version 2017-11-22 09:59:29
 */
public class ShellManager {

    private static Runtime runtime;

    static {
        runtime = Runtime.getRuntime();
    }

    /**
     * 执行Linux Shell命令
     *
     * @param command Shell命令。例如："mvn -f /root/Desktop/zm-sso/pom.xml install" mvn命令必须已添加在环境变量中，如果没有必须写绝对路径。
     * @return Shell退出状态码。0 命令成功结束、1 通用未知错误、2 误用Shell命令、126 命令不可执行、127 没找到命令、128 无效退出参数、128+x Linux信号x的严重错误、130 命令通过Ctrl+C控制码越界、255 退出码越界
     * @throws java.io.IOException
     * @throws InterruptedException
     */
    public static int exec(String command) throws IOException, InterruptedException {
        String[] cmds = {"/bin/bash", "-c", command};
        Process pro = runtime.exec(cmds);

        final InputStream is1 = pro.getInputStream();
        // 启动单独的线程来清空p.getInputStream()的缓冲区
        new Thread(new Runnable() {
            public void run() {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new InputStreamReader(is1));
                    String line = null;
                    while ( (line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (br != null) br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        InputStream is2 = pro.getErrorStream();
        BufferedReader br2 = null;
        try {
            br2 = new BufferedReader(new InputStreamReader(is2));
            String line = null;
            while ((line = br2.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br2 != null) br2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //在waitFor之前将控制台输出缓冲区清空，避免程序卡住
        pro.waitFor();

        return pro.exitValue();
    }

}
