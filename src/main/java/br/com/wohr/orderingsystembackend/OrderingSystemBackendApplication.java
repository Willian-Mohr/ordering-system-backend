package br.com.wohr.orderingsystembackend;

import br.com.wohr.orderingsystembackend.domain.Categoria;
import br.com.wohr.orderingsystembackend.repositories.CateroriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class OrderingSystemBackendApplication implements CommandLineRunner {

    @Autowired
    CateroriaRepository cateroriaRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrderingSystemBackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        cateroriaRepository.saveAll(Arrays.asList(cat1, cat2));
    }
}
