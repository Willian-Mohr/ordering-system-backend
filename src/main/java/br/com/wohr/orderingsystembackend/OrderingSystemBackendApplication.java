package br.com.wohr.orderingsystembackend;

import br.com.wohr.orderingsystembackend.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderingSystemBackendApplication implements CommandLineRunner {


    @Autowired
    private S3Service s3Service;

    public static void main(String[] args) {
        SpringApplication.run(OrderingSystemBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        s3Service.uploadFile("W:\\Udemy\\Spring Boot\\Projetos\\PDF\\Secao-7\\uploadAmazonS3.jpg");
    }
}
