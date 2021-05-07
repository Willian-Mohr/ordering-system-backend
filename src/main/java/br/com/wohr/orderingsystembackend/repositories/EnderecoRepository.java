package br.com.wohr.orderingsystembackend.repositories;

import br.com.wohr.orderingsystembackend.domain.Cliente;
import br.com.wohr.orderingsystembackend.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
