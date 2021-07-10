package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Pedido;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendOrderConfirmationEmail(Pedido obj);

    void sendMail(SimpleMailMessage msg);
}