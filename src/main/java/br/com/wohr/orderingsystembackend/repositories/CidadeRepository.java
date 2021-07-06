package br.com.wohr.orderingsystembackend.repositories;

import br.com.wohr.orderingsystembackend.domain.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {
}
