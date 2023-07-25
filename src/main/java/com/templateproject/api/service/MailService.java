package com.templateproject.api.service;

import com.templateproject.api.entity.UserMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    private final TokenService tokenService;

    public MailService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public void SendNotification(UserMail userMail) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        String token = tokenService.generateTokenRetrievePassword(userMail.getEmailAddress());
        mail.setTo(userMail.getEmailAddress());
        mail.setFrom("myserieslib@gmail.com");
        mail.setSubject("Réinitialisation du mot de passe");
        String link = "Pour réinitialiser votre mot de passe, merci de suivre ce lien : http://localhost:4200/retrieve-password/" + token + " . Attention, il expirera dans 15 minutes.";
        mail.setText(link);
        javaMailSender.send(mail);
    }
}
