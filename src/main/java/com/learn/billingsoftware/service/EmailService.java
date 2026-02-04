package com.learn.billingsoftware.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailWithAttachment(String toEmail, String subject, String body, ByteArrayInputStream pdfStream, String fileName) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            // true = multipart (allows attachments)
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body);

            // Convert Stream to Resource for attachment
            byte[] bytes = pdfStream.readAllBytes();
            ByteArrayResource pdfResource = new ByteArrayResource(bytes);

            helper.addAttachment(fileName, pdfResource);

            javaMailSender.send(mimeMessage);
            System.out.println("Email sent successfully to " + toEmail);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}