package com.classroom.service.mail.impl;

import com.classroom.constant.EmailConstants;
import com.classroom.constant.EnvConstants;
import com.classroom.exception.InvalidServiceRequestException;
import com.classroom.service.mail.EmailService;
import com.classroom.web.payload.request.mail.HtmlEmailRequest;
import com.classroom.config.properties.SystemProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * SmtpEmailServiceImpl Class has the operation of email sending in a real time.
 *
 * @author Md Jewel
 * @version 1.0
 * @see EmailService
 * @since 1.0
 */
@Slf4j
@Service
@Profile({
  EnvConstants.DEVELOPMENT,
  EnvConstants.PRODUCTION,
  EnvConstants.INTEGRATION_TEST,
  EnvConstants.INTEGRATION_TEST_CI
})
public class SmtpEmailServiceImpl extends AbstractEmailServiceImpl {

  @Autowired private SystemProperties systemProps;
  @Autowired private JavaMailSender mailSender;
  @Autowired private TemplateEngine templateEngine;

  /**
   * Sends an email with the provided simple mail message object.
   *
   * @param simpleMailMessage the simple mail message.
   */
  @Override
  public void sendMail(final SimpleMailMessage simpleMailMessage) {
    LOG.info("Sending mail with content {}", simpleMailMessage);
    mailSender.send(simpleMailMessage);
    LOG.info( EmailConstants.MAIL_SUCCESS_MESSAGE);
  }

  @Override
  public void sendHtmlEmail(final HtmlEmailRequest emailRequest) {
    try {
      LOG.info("Template used is {}", templateEngine);
      LOG.debug("Sending html email with details {}", emailRequest);

      MimeMessage mimeMessage = prepareMimeMessage(emailRequest);
      mailSender.send(mimeMessage);

      LOG.info(EmailConstants.MAIL_SUCCESS_MESSAGE);
    } catch (MessagingException | FileNotFoundException | UnsupportedEncodingException e) {
      throw new InvalidServiceRequestException(e);
    }
  }

  @Override
  public void sendHtmlEmailWithAttachment(final HtmlEmailRequest emailRequest) {
    try {
      LOG.info("Template used is {}", templateEngine);
      LOG.debug("Sending html email with details {}", emailRequest);

      mailSender.send(prepareMimeMessage(emailRequest));

      LOG.info(EmailConstants.MAIL_SUCCESS_MESSAGE);
    } catch (MessagingException | FileNotFoundException | UnsupportedEncodingException e) {
      throw new InvalidServiceRequestException(e);
    }
  }

  /**
   * Prepares a MimeMessage with provided EmailFormat.
   *
   * @param emailFormat the emailFormat
   * @return MimeMessage the MimeMessage
   */
  private MimeMessage prepareMimeMessage(final HtmlEmailRequest emailFormat)
      throws MessagingException, UnsupportedEncodingException, FileNotFoundException {

    var context = new Context();
    context.setVariable(EmailConstants.URLS, emailFormat.getUrls());
    emailFormat.setContext(context);

    var withAttachment = CollectionUtils.isNotEmpty(emailFormat.getAttachments());

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper =
        new MimeMessageHelper(mimeMessage, withAttachment, StandardCharsets.UTF_8.name());
    helper.setTo(emailFormat.getTo());
    helper.setSentDate(new Date());
    // Set multiple recipients and cc them as needed
    if (!emailFormat.getRecipients().isEmpty()) {
      helper.setCc(emailFormat.getRecipients().toArray(String[]::new));
    }
    String body = templateEngine.process(emailFormat.getTemplate(), emailFormat.getContext());
    helper.setText(body, true);
    helper.setSubject(emailFormat.getSubject());
    // set up the senders address with the given name
    setFromAndReplyTo(emailFormat, helper);

    if (withAttachment) {
      addAttachments(emailFormat, helper);
    }
    return mimeMessage;
  }

  /**
   * Setup the senders address using Internet Address.
   *
   * @param emailFormat the email format
   * @param helper the mime message helper
   * @throws UnsupportedEncodingException if there is any issue with encoding
   * @throws MessagingException if there is any exception processing the message
   */
  private void setFromAndReplyTo(final HtmlEmailRequest emailFormat, final MimeMessageHelper helper)
      throws UnsupportedEncodingException, MessagingException {

    InternetAddress internetAddress;
    if (Objects.nonNull(emailFormat.getFrom()) && Objects.nonNull(emailFormat.getSender())) {
      internetAddress =
          new InternetAddress(emailFormat.getFrom(), emailFormat.getSender().getName());
    } else if (Objects.nonNull(emailFormat.getFrom())) {
      internetAddress = new InternetAddress(emailFormat.getFrom(), systemProps.getName());
    } else {
      internetAddress = new InternetAddress(systemProps.getEmail(), systemProps.getName());
    }

    helper.setFrom(String.valueOf(internetAddress));
    helper.setReplyTo(internetAddress);

    if (Objects.nonNull(emailFormat.getReceiver())) {
      internetAddress =
          new InternetAddress(emailFormat.getTo(), emailFormat.getReceiver().getName());
      helper.setTo(internetAddress);
    }
    helper.setTo(internetAddress);
  }
}
