package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Categoria;
import br.com.wohr.orderingsystembackend.repositories.CateroriaRepository;
import br.com.wohr.orderingsystembackend.services.exceptions.DataIntegrityException;
import br.com.wohr.orderingsystembackend.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CateroriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());
        return repo.save(obj);
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que contenha produtos");
        }
    }
}
