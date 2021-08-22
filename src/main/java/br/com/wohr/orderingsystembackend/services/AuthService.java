package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private ClienteService clienteService;

    public void forgotPassword(String email, String token, String url) {

        Cliente cliente = clienteService.findByEmail(email);
        emailService.sendPasswordResetEmailHtml(cliente, token, url);
    }


}
