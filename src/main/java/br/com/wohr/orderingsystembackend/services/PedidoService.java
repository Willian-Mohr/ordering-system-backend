package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Pedido;
import br.com.wohr.orderingsystembackend.repositories.PedidoRepository;
import br.com.wohr.orderingsystembackend.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }
}
