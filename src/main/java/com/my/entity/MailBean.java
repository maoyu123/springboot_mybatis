package com.my.entity;

import java.io.Serializable;

public class MailBean implements Serializable{
    private static final long serialVersionUID = 1972237199841092679L;

    private String recipient;
    private String subject;
    private String content;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
