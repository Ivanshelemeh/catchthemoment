package com.catchthemoment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserConfirmMailService {
    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(@NotNull String email) {
        if (!email.isEmpty()) {
            SimpleMailMessage mailMessage = createSimpleMessage(email);
            mailSender.send(mailMessage);
        }
        log.error("mail is not valid or empty ! ");
        throw new MailSendException("Mail is not valid"+ email);
    }

    public SimpleMailMessage createSimpleMessage(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Complete Registration!");
        message.setText("To confirm your account, please click here : "
                + "http://localhost:8080/confirm-account?token=" + createMailToken());
        return message;

    }


    private String createMailToken() {
        String confToken = RandomStringUtils.random(20);
        return confToken;
    }
}
