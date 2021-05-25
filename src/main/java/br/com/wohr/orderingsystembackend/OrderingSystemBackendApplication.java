package br.com.wohr.orderingsystembackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderingSystemBackendApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(OrderingSystemBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
