package br.com.wohr.orderingsystembackend.repositories;

import br.com.wohr.orderingsystembackend.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
