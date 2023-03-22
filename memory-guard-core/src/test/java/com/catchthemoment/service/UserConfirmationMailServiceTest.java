package com.catchthemoment.service;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserConfirmationMailServiceTest {

    @Mock
    private JavaMailSender sender;

    @InjectMocks
    private UserConfirmMailService userConfirmMailService;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin"))
            .withPerMethodLifecycle(false);

    @Test
    @DisplayName("Should create message with content")
    void should_create_message_success() throws MessagingException, IOException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("user@spring.io");
        mailMessage.setTo("info@memorynotfound.com");
        mailMessage.setSubject("Spring Mail Integration Testing ");
        mailMessage.setText("How to !");
        sender.send(mailMessage);

        await().atMost(2, SECONDS).untilAsserted(() -> {
            MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
            assertEquals(0, receivedMessages.length);
            Exception exception = assertThrows(
                    MailSendException.class,()->userConfirmMailService.sendConfirmationEmail("")
            );
            assertEquals("Mail is not valid", exception.getMessage());
        });
    }

}
