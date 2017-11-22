package com.jthinking.deploy.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目部署环境变量设置
 * @author JiaBochao
 * @version 2017-11-22 09:55:59
 */
public class Locations {
    private String projectName;
    private List<String> tomcatHomes = new ArrayList<String>();
    private String backupDir;
    private String unzipDir;
    private String uploadDir;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<String> getTomcatHomes() {
        return tomcatHomes;
    }

    public void setTomcatHomes(List<String> tomcatHomes) {
        this.tomcatHomes = tomcatHomes;
    }

    public String getBackupDir() {
        return backupDir;
    }

    public void setBackupDir(String backupDir) {
        this.backupDir = backupDir;
    }

    public String getUnzipDir() {
        return unzipDir;
    }

    public void setUnzipDir(String unzipDir) {
        this.unzipDir = unzipDir;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
