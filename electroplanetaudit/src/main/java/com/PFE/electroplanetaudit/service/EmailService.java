package com.PFE.electroplanetaudit.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendTwoFACode(String toEmail, String code) {
        try {
            Context context = new Context();
            context.setVariable("code", code);

            String htmlContent = templateEngine.process("email/2fa-code", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("🔐 ElectroPlanet Audit - Votre code de vérification");
            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }

    public void sendPasswordResetCode(String toEmail, String code) {
        try {
            // Create Thymeleaf context
            Context context = new Context();
            context.setVariable("code", code);
            context.setVariable("year", java.time.Year.now().getValue());
            context.setVariable("purpose", "réinitialisation de votre mot de passe");

            // Process the HTML template
            String htmlContent = templateEngine.process("email/reset-password-code", context);

            // Create email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("🔐 ElectroPlanet Audit - Réinitialisation de votre mot de passe");
            helper.setText(htmlContent, true);

            mailSender.send(message);

            System.out.println("Reset code sent to: " + toEmail + " with code: " + code);

        } catch (MessagingException e) {
            System.err.println("Failed to send reset email: " + e.getMessage());
        }
    }
}