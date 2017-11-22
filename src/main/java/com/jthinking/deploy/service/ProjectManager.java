package com.jthinking.deploy.service;


import com.jthinking.deploy.pojo.Locations;
import com.jthinking.deploy.util.PropConf;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目工具类
 * @author JiaBochao
 * @version 2017-11-22 09:59:02
 */
public class ProjectManager {

    public static void deploy(MultipartFile file, Locations locations) {
        List<File> tempList = new ArrayList<File>();
        WebSocketManager webSocketManager = new WebSocketManager(PropConf.getProp("WEB_SOCKET_URL") + "?project=" + locations.getProjectName() + "&module=deploy");
        try {
            long startTime = System.currentTimeMillis();

            File upload = UploadManager.upload(file, locations.getUploadDir());
            if (upload == null) {
                webSocketManager.sendMessage("文件上传出错!");
                throw new RuntimeException();
            }
            webSocketManager.sendMessage("文件上传成功。上传位置：" + upload.toString());
            webSocketManager.sendMessage("正在解压文件......");
            File zipFile = ZipManager.unzip(upload, locations.getUnzipDir());
            if (zipFile == null) {
                webSocketManager.sendMessage("文件解压出错！");
                throw new RuntimeException();
            }
            tempList.add(zipFile);
            webSocketManager.sendMessage("文件解压成功。解压位置：" + zipFile.toString());
            File pomFile = new File(zipFile.toString() + "/pom.xml");
            webSocketManager.sendMessage("找到pom.xml文件");
            webSocketManager.sendMessage("正在打war包...，这个可能会花费一些时间。");
            String command = "/usr/local/maven3.2/bin/mvn -f " + pomFile.toString() + " install";
            int status1 = ShellManager.exec(command);
            if (status1 != 0) {
                webSocketManager.sendMessage("maven打war包出错!");
                throw new RuntimeException();
            }
            webSocketManager.sendMessage("打war包成功");
            String artifactId = XMLFileManager.getTagContent("artifactId", pomFile);
            if (artifactId == null) {
                throw new RuntimeException();
            }
            webSocketManager.sendMessage("正在获取war文件...");
            File warFile = new File(zipFile.toString() + "/target/" + artifactId + ".war");
            if (!warFile.exists()) {
                webSocketManager.sendMessage("找不到war文件");
                throw new RuntimeException();
            }
            webSocketManager.sendMessage("正在部署war包，并重启Tomcat集群...");
            for (String tomcatHome : locations.getTomcatHomes()) {
                webSocketManager.sendMessage("正在关闭Tomcat负载均衡" + tomcatHome + "...");
                int status4 = ShellManager.exec(tomcatHome + "/bin/shutdown.sh");
                if (status4 != 0) {
                    webSocketManager.sendMessage("关闭失败");
                    throw new RuntimeException();
                }
                webSocketManager.sendMessage("正在部署负载均衡" + tomcatHome + "war包...");
                int status2 = ShellManager.exec("cp -rf " + warFile.toString() + " " + tomcatHome + "/webapps/ROOT.war");
                if (status2 != 0) {
                    webSocketManager.sendMessage("war包部署失败");
                    throw new RuntimeException();
                }
                webSocketManager.sendMessage("删除原有的ROOT目录");
                int status5 = ShellManager.exec("rm -rf " + tomcatHome + "/webapps/ROOT");
                if (status5 != 0) {
                    webSocketManager.sendMessage("删除原有的ROOT目录失败");
                    throw new RuntimeException();
                }
                webSocketManager.sendMessage("正在启动Tomcat负载均衡" + tomcatHome + "...");
                int status6 = ShellManager.exec(tomcatHome + "/bin/startup.sh");
                if (status6 != 0) {
                    webSocketManager.sendMessage("Tomcat负载均衡" + tomcatHome + "启动失败");
                    throw new RuntimeException();
                }
                webSocketManager.sendMessage("Tomcat负载均衡" + tomcatHome + "部署重启成功！");
                webSocketManager.sendMessage("---------------------------------------------");
            }
            long endTime = System.currentTimeMillis();
            webSocketManager.sendMessage("项目部署完毕！共耗时：" + ((endTime - startTime) / 1000.0) + "秒");
        } catch (Exception e) {
            e.printStackTrace();
            webSocketManager.sendMessage("<span style='color:red;'>程序已终止！</span>");
            throw new RuntimeException(e);
        } finally {
            //清理中间垃圾数据
            webSocketManager.sendMessage("正在清理中间垃圾数据......");
            for (File tempFile : tempList) {
                FileManager.deletesFile(tempFile);
            }
            webSocketManager.sendMessage("完成！");
        }

    }
}
