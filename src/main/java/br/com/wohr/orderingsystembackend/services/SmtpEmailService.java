package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.services.exceptions.SpringMailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailService extends AbstractEmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);

    @Override
    public void sendMail(SimpleMailMessage msg) {
        try {
            LOG.info("Enviando email...");
            javaMailSender.send(msg);
            LOG.info("Email enviado");
        } catch (MailAuthenticationException e) {
            LOG.info("Falha no envio de email");
            throw new SpringMailException("Falha na autenticação do email do remetente. Entre em contato com o suporte!");
        }
    }

    @Override
    public void sendHtmlEmail(MimeMessage msg) {
        try {
            LOG.info("Enviando email HTML...");
            javaMailSender.send(msg);
            LOG.info("Email enviado");
        } catch (MailAuthenticationException e) {
            LOG.info("Falha no envio de email");
            throw new SpringMailException("Falha na autenticação do email do remetente. Entre em contato com o suporte!");
        }
    }
}
