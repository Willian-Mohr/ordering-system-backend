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
    public void sendPasswordResetEmailHtml(Cliente obj, String token, String url) {
        try {
            MimeMessage mm = preparePasswordResetMessage(obj, token, url);
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendPasswordResetEmail(obj, token, url);
        }

    }

    protected MimeMessage preparePasswordResetMessage(Cliente obj, String token, String url) throws MessagingException {

        String link = linkGeneration(token, url);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(obj.getEmail());
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setSubject("Recuperação de senha");
        mimeMessageHelper.setSentDate(new Date(System.currentTimeMillis()));
        mimeMessageHelper.setText(htmlFromTemplatePasswordReset(obj, link), true);

        return mimeMessage;
    }

    private String linkGeneration(String token, String url) {

        return url + "/clientes/changePassword?Authorization=" + token;
    }

    protected String htmlFromTemplatePasswordReset(Cliente obj, String link) {
        Context context = new Context();
        context.setVariable("cliente", obj);
        context.setVariable("link", link);
        return templateEngine.process("passwordReset/recuperacaoSenha", context);
    }

    @Override
    public void sendPasswordResetEmail(Cliente cliente, String token, String url) {
        SimpleMailMessage sm = prepareSimplePasswordResetEmail(cliente, token, url);
        sendMail(sm);
    }

    protected SimpleMailMessage prepareSimplePasswordResetEmail(Cliente cliente, String token, String url) {

        String link = linkGeneration(token, url);

        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(cliente.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de recuperação de senha");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("Seu link para alteração de senha é " + link + " link válido por 1 hora");
        return sm;
    }

}
