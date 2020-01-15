package com.my.util;

import com.my.entity.MailBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailUtil {
    @Value("${spring.mail.username}")
    private String MAIL_SENDER; //邮件发送者

    @Autowired
    private JavaMailSender javaMailSender;

    private Logger logger = LoggerFactory.getLogger(MailUtil.class);

    public void sendSimpleMail(MailBean mailBean){
        try {
            SimpleMailMessage mailMessage=new SimpleMailMessage();
            mailMessage.setFrom(MAIL_SENDER);
            mailMessage.setTo(mailBean.getRecipient());
            mailMessage.setSubject(mailBean.getSubject());
            mailMessage.setText(mailBean.getContent());
//            mailMessage.copyTo();     抄送人
            javaMailSender.send(mailMessage);
            System.out.println("邮件发送成功~~~");
        } catch (Exception e) {
            logger.error("邮件发送失败"+e.getMessage());
        }
    }

    //html格式邮件发送
    public void sendHtmlMail(MailBean mailBean){
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            // true  表示需要创建一个multipart message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            //邮件抄送人  在mailBean中添加属性
//            mimeMessageHelper.addCc();
            mimeMessageHelper.setText(mailBean.getContent(),true);
            javaMailSender.send(mimeMailMessage);
            System.out.println("邮件发送成功，请查收~~~");
        } catch (Exception e) {
            logger.error("邮件发送失败",e.getMessage());
        }
    }

    //附件格式 邮件发送
    public void sendAttachmentMail(MailBean mailBean){
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent(),true);
            //文件路径  现阶段是写死在代码中，之后可以当参数传过来，也可以在mailBean添加属性absolutePath
            String absolutePath ="D:\\www\\picture\\fly.jpg";
            FileSystemResource file = new FileSystemResource(new File(absolutePath));
            String fileName = absolutePath.substring(absolutePath.lastIndexOf(File.separator));
            //添加附件，参数一表示添加到emali中，参数二是图片资源
            mimeMessageHelper.addAttachment(fileName,file);
            //添加多个附件
//            mimeMessageHelper.addAttachment(fileName,file);
            javaMailSender.send(mimeMailMessage);
            System.out.println("邮件发送成功，请查收~~~");
        } catch (Exception e) {
            logger.error("邮件发送失败",e.getMessage());
        }
    }

    //静态资源格式 邮件发送
    public void sendInlineMail(MailBean mailBean) {
        MimeMessage mimeMailMessage = null;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom(MAIL_SENDER);
            mimeMessageHelper.setTo(mailBean.getRecipient());
            mimeMessageHelper.setSubject(mailBean.getSubject());
            mimeMessageHelper.setText(mailBean.getContent(), true);
            //文件路径
            String absolutePath = "D:\\www\\picture\\fly.jpg";
            FileSystemResource file = new FileSystemResource(new File(absolutePath));
            //FileSystemResource file = new FileSystemResource(new File("src/main/resources/static/image/email.png"))
            //添加多个图片可以使用多条 <img src='cid:" + rscId + "' > 和 mimeMessageHelper.addInline(rscId, res) 来实现
            mimeMessageHelper.addInline("picture", file);
            System.out.println("开始发送邮件~~~");
            javaMailSender.send(mimeMailMessage);
            System.out.println("邮件发送成功，请注意查收~~~");
        } catch (Exception e) {
            logger.error("邮件发送失败", e.getMessage());
        }
    }

}
