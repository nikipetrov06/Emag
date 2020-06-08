package com.example.emag.utils;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendEmailUtil {

  //send email if discount or quantity update are made
  private static final String SENDER = "emag.no.reply@gmail.com";

  @SneakyThrows
  public static void sendMail(String to, String subject, String body) {

    final String username = "emag.no.reply@gmail.com";
    final String password = "emag2020";

    Properties prop = new Properties();
    prop.put("mail.smtp.auth", "true");
    prop.put("mail.smtp.starttls.enable", "true");   //TLS
    prop.put("mail.smtp.host", "smtp.gmail.com");
    prop.put("mail.smtp.port", "587");
    prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

    Session session = Session.getInstance(prop,
        new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
          }
        });

    Message message = new MimeMessage(session);
    message.setFrom(new InternetAddress(SENDER));
    message.setRecipients(
        Message.RecipientType.TO,
        InternetAddress.parse(to));
    message.setSubject(subject);
    message.setText(body);
    Transport.send(message);
    log.info("Finished sending e-mails");
  }
}
