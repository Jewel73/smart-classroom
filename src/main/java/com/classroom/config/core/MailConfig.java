package com.classroom.config.core;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


/**
 * This class provides every bean, and other configurations needed to be used in the mail
 * phase.
 *
 * @author md jewel
 * @version 1.0
 * @since 1.0
 */

@Configuration
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class MailConfig {

  @Value("${spring.mail.host}")
  private String host;

  @Value("${spring.mail.port}")
  private int port;

  @Value("${spring.mail.username}")
  private String username;

  @Value("${spring.mail.password}")
  private String password;

  @Value("${spring.mail.protocol}")
  private String protocol;

  @Value("${spring.mail.properties.mail.smtp.auth}")
  private String smtpAuth;

  @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
  private String smtpStarttlsEnable;

  /**
   * JavaMail sender bean
   *
   * @author md jewel
   * @version 1.0
   * @since 1.0
   */
  @Bean
  public JavaMailSender mailSender() {
    return new JavaMailSenderImpl();
  }

  @Bean
  public JavaMailSender mailSender1() {
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    mailSender.setHost(host);
    mailSender.setPort(port);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    Properties props = mailSender.getJavaMailProperties();
    props.put("mail.transport.protocol", protocol);
    props.put("mail.smtp.auth", smtpAuth);
    props.put("mail.smtp.starttls.enable", smtpStarttlsEnable);

    return mailSender;
  }
}
