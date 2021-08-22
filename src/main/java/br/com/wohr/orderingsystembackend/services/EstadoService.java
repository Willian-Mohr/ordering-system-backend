package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Estado;
import br.com.wohr.orderingsystembackend.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<Estado> findAll(){
        return estadoRepository.findAllByOrderByNome();
    }
}
