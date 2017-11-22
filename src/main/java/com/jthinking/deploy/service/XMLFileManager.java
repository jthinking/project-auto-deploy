package com.jthinking.deploy.service;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * XML工具类
 * @author JiaBochao
 * @version 2017-11-22 10:00:58
 */
public class XMLFileManager extends FileManager {

	public static String getTagContent(String tagName, File xmlFile) {
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        String xml = null;
        try {
            in = new FileInputStream(xmlFile);
            out = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = -1;
            while ( (len = in.read(buffer, 0, bufferSize)) != -1) {
                out.write(buffer, 0, len);
            }
            xml = out.toString("utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Document document = null;
        try {
            document = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
        Element root = document.getRootElement();
        Element tag = root.element(tagName);
		return tag.getTextTrim();
	}

}
