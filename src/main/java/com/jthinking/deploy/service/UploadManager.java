package com.jthinking.deploy.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 上传工具类
 * @author JiaBochao
 * @version 2017-11-22 10:00:12
 */
public class UploadManager {

	public static File upload(MultipartFile file, String targetDir) {
        BufferedOutputStream out = null;
        try {
            //设置文件存放位置
            String uploadUrl = targetDir;
            if (file == null || file.isEmpty()) {
                return null;
            }
            byte[] bytes = file.getBytes();
            String[] nameArr = file.getOriginalFilename().split("\\.");
            String fileType = nameArr[nameArr.length-1];
            SimpleDateFormat dateFormat = new SimpleDateFormat("/yyyy/MM/dd/");
            String dir = dateFormat.format(new Date());
            String fileName = System.currentTimeMillis() + "." + fileType;
            // 文件的绝对路径名
            String absUrl = uploadUrl + "/" + dir + fileName;
            File path = new File(uploadUrl + "/" + dir);
            if (!path.exists()) {
                path.mkdirs();
            }
            out = new BufferedOutputStream(new FileOutputStream(absUrl));
            out.write(bytes);
            out.flush();
            return new File(absUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

	}

}
