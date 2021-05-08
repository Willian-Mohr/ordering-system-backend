package br.com.wohr.orderingsystembackend.repositories;

import br.com.wohr.orderingsystembackend.domain.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
}
