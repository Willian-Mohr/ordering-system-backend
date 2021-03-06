package br.com.wohr.orderingsystembackend.repositories;

import br.com.wohr.orderingsystembackend.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateroriaRepository extends JpaRepository<Categoria, Integer> {
}
