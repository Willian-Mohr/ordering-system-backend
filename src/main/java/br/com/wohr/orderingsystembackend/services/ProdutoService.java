package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Categoria;
import br.com.wohr.orderingsystembackend.domain.Pedido;
import br.com.wohr.orderingsystembackend.domain.Produto;
import br.com.wohr.orderingsystembackend.repositories.CateroriaRepository;
import br.com.wohr.orderingsystembackend.repositories.ProdutoRepository;
import br.com.wohr.orderingsystembackend.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CateroriaRepository cateroriaRepository;

    public Produto find(Integer id) {
        Optional<Produto> obj = produtoRepository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = cateroriaRepository.findAllById(ids);
        return produtoRepository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }
}
