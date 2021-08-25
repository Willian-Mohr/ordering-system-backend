package br.com.wohr.orderingsystembackend.config;

import br.com.wohr.orderingsystembackend.services.EmailService;
import br.com.wohr.orderingsystembackend.services.SmtpEmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }
}
