package com.jthinking.deploy.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.Security;
import java.util.Properties;

/**
 * 使用Java Mail发送邮箱
 * @author JiaBochao
 * @version 2017-11-17 10:22:32
 */
public class JavaMail {
    /**
     * 发送者邮箱，必须开通smtp服务
     */
    private static final String SENDER = PropConf.getProp("email.sender");

    /**
     * smtp密码
     */
    private static final String PASSWORD = PropConf.getProp("email.password");

    /**
     * 邮件显示的发件人名称
     */
    private static final String SENDER_NAME = PropConf.getProp("email.sender.name");

    /**
     * smtp服务器主机
     */
    private static final String SMTP_HOST = PropConf.getProp("email.smtp.host");

    /**
     * smtp端口，默认465，一般不需要改
     */
    private static final String SMTP_PORT = PropConf.getProp("email.smtp.port");

    /**
     * 套接字工厂端口
     */
    private static final String SMTP_SOCKET_FACTORY_PORT = PropConf.getProp("email.smtp.socket.factory.port");

    /**
     * 发送邮件
     * @param receiver 接收者邮箱地址
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public static void send(String receiver, String subject, String content) {
        try {
            //设置SSL连接、邮件环境
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", SMTP_HOST);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", SMTP_PORT);
            props.setProperty("mail.smtp.socketFactory.port", SMTP_SOCKET_FACTORY_PORT);
            props.setProperty("mail.smtp.auth", "true");
            //建立邮件会话
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                //身份认证
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER, PASSWORD);
                }
            });
            //建立邮件对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER, SENDER_NAME));
            message.setRecipients(Message.RecipientType.TO, receiver);
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(content, "text/html;charset=UTF-8");
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(mimeBodyPart);

            message.setContent(mimeMultipart);
            message.saveChanges();

            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
