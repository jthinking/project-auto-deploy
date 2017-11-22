package com.jthinking.deploy.service;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

/**
 * Zip解压工具类
 *
 * @author JiaBochao
 * @version 2017-11-22 10:02:11
 */
public class ZipManager {

    public static File unzip(File zipFile, String targetDir) {
        ZipFile zip = null;
        try {
            zip = new ZipFile(zipFile, "utf-8");
            Enumeration<ZipArchiveEntry> entries = zip.getEntries();
            ZipArchiveEntry zipArchiveEntry;
            File result = null;
            int i = 0;
            while (entries.hasMoreElements()) {
                zipArchiveEntry = entries.nextElement();
                File file = new File(targetDir + "/" + zipArchiveEntry.getName());
                if (i == 0) result = file;
                if (zipArchiveEntry.isDirectory()) {
                    if (!file.exists()) file.mkdirs();
                } else {
                    FileOutputStream out = null;
                    InputStream in = null;
                    try {
                        out = new FileOutputStream(file);
                        in = zip.getInputStream(zipArchiveEntry);
                        int b = -1;
                        byte[] bytes = new byte[1024];
                        while ((b = in.read(bytes, 0, bytes.length)) != -1) {
                            out.write(bytes, 0, b);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    } finally {
                        if (in != null) in.close();
                        if (out != null) out.close();
                    }
                }
                i++;
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (zip != null) zip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
