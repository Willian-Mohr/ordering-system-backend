package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Cliente;
import br.com.wohr.orderingsystembackend.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;

public interface EmailService {

    void sendOrderConfirmationHtmlEmail(Pedido obj);

    void sendOrderConfirmationEmail(Pedido obj);

    void sendPasswordResetEmailHtml(Cliente obj, String newPass, String url);

    void sendPasswordResetEmail(Cliente obj, String token, String url);

    void sendHtmlEmail(MimeMessage msg);

    void sendMail(SimpleMailMessage msg);

}
