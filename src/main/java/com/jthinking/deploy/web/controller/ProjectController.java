package com.jthinking.deploy.web.controller;


import com.jthinking.deploy.pojo.Locations;
import com.jthinking.deploy.service.ProjectManager;
import com.jthinking.deploy.util.UserThreadLocal;
import com.jthinking.deploy.web.exception.UserException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 项目部署
 * 上传项目源码，在服务器通过Maven编译、打成war包
 * 包括上传、编译、打war包、备份、部署、重启服务
 * @author JiaBochao
 * @version 2017-9-26 08:28:05
 */
@Controller
@RequestMapping("/project")
public class ProjectController {

    //上传标志位，只允许一个线程访问
    private Boolean oaUploading = false;

    @RequestMapping("/deploy")
    @ResponseBody
    public String deploy(MultipartFile file, Locations locations) {
        try {
            if (oaUploading) {
                throw new UserException("禁止操作！当前有其他管理员正在部署项目，您可在下面的实时监控中查看进度");
            }
            //将标志位设为正在上传
            oaUploading = true;
            //检查环境变量
            if (locations.getProjectName() == null) {
                throw new UserException("项目名不能为空");
            }
            if (locations.getBackupDir() == null) {
                throw new UserException("项目备份目录不能为空");
            }
            if (locations.getUnzipDir() == null) {
                throw new UserException("压缩包解压目录不能为空");
            }
            if (locations.getUploadDir() == null) {
                throw new UserException("上传保存目录不能为空");
            }
            if (locations.getTomcatHomes() == null || locations.getTomcatHomes().size() == 0) {
                throw new UserException("Tomcat根目录不能为空");
            }
            //部署项目
            ProjectManager.deploy(file, locations);
            return "完成";
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        } finally {
            //将标志位复位
            oaUploading = false;
        }

    }

    @RequestMapping("/deployPage")
    public String deployPage(Model model) {
        model.addAttribute("username", UserThreadLocal.getUsername());
        return "project-deploy";
    }

}
