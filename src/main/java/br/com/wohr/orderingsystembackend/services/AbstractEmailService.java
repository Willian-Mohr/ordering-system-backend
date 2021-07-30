package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Cliente;
import br.com.wohr.orderingsystembackend.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("$default.sender")
    private String sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    // Confirmação de pedido
    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(obj);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }

    }

    protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(obj.getCliente().getEmail());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setSubject("Pedido confirmado! Código: " + obj.getId());
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlFromTemplatePedido(obj), true);

        return mimeMessage;
    }

    protected String htmlFromTemplatePedido(Pedido obj) {
        Context context = new Context();
        context.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", context);
    }

    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendMail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getCliente().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Pedido confirmado! Código: " + obj.getId());
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    // Recuperação de senha
    @Override
    public void sendPasswordResetEmailHtml(Cliente obj, String newPass) {
        try {
            MimeMessage mm = preparePasswordResetMessage(obj, newPass);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendPasswordResetEmail(obj, newPass);
        }

    }

    protected MimeMessage preparePasswordResetMessage(Cliente obj, String newPass) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(obj.getEmail());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setSubject("Recuperação de senha");
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlFromTemplatePasswordReset(obj, newPass), true);

        return mimeMessage;
    }

    protected String htmlFromTemplatePasswordReset(Cliente obj, String newPass) {
        Context context = new Context();
        context.setVariable("usuario", obj);
        context.setVariable("senha", newPass);
        return templateEngine.process("passwordReset/recuperacaoSenha", context);
    }

    @Override
    public void sendPasswordResetEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = prepareSimplePasswordResetEmail(cliente, newPass);
        sendMail(sm);
    }

    protected SimpleMailMessage prepareSimplePasswordResetEmail(Cliente cliente, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de recuperação de senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

}
