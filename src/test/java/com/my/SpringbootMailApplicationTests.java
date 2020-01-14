package com.my;

import com.my.entity.MailBean;
import com.my.util.DateUtils;
import com.my.util.MailUtil;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootMailApplicationTests {
    @Autowired
    private MailUtil mailUtil;
    private static final String RECIPINET = "763935210@qq.com";

    //简单邮件测试(文本)
    public void sendSimpleMail(){
        MailBean mailBean =new MailBean();
        mailBean.setRecipient(RECIPINET);
        mailBean.setSubject("springboot集成mail测试");
        mailBean.setContent("springboot集成mail测试发送一个简单格式的邮件，时间："+ DateUtils.formatTime(new Date()));
        mailUtil.sendSimpleMail(mailBean);
    }


    //html格式的邮件
    public void sendHTMLMail(){
        MailBean mailBean =new MailBean();
        mailBean.setRecipient(RECIPINET);
        mailBean.setSubject("这个是html格式的邮件");
        StringBuilder sb = new StringBuilder();
        sb.append("<h2>galgadot朵吧 springboot测试发送html邮件</h2>")
                .append("<p style='text-align:left'>这是个html格式的邮件</p>")
                .append("<p>时间："+DateUtils.formatTime(new Date())+"</p>");
        mailBean.setContent(sb.toString());
        mailUtil.sendHtmlMail(mailBean);
    }

    public void sendAttachmentMail(){
        MailBean mailBean = new MailBean();
        mailBean.setRecipient(RECIPINET);
        mailBean.setSubject("springboot集成mail测试 带有附件格式的邮件");
        mailBean.setContent("springboot集成mail测试发送一个带有附件格式的邮件，时间："+ DateUtils.formatTime(new Date()));
        mailUtil.sendAttachmentMail(mailBean);
    }

//    @Test 发送失败，待解决
//    public void sendInlineMail() {
//        MailBean mailBean = new MailBean();
//        //id,目前写死了，可根据需要封装
//        String rscId = "picture";
//        String content="<html><body>这是有图片的邮件：<img src=\'cid:" + rscId + "\' ></body></html>";
//        mailBean.setRecipient(RECIPINET);
//        mailBean.setSubject("SpringBootMail之这是一封有静态资源格式的邮件");
//        mailBean.setContent(content);
//
//        mailUtil.sendInlineMail(mailBean);
//    }
}
