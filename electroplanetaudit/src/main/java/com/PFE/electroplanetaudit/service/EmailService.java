package com.PFE.electroplanetaudit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendTwoFACode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject("🔐 ElectroPlanet Audit - Votre code de vérification");
        message.setText(String.format(
                "Bonjour,\n\n" +
                        "Votre code de vérification à deux facteurs est : %s\n\n" +
                        "Ce code expirera dans 15 minutes.\n\n" +
                        "Cordialement,\n" +
                        "Plateforme Audit ElectroPlanet",
                code
        ));

        mailSender.send(message);
    }
}