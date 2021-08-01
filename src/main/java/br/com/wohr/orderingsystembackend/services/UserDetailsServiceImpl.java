package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Cliente;
import br.com.wohr.orderingsystembackend.repositories.ClienteRepository;
import br.com.wohr.orderingsystembackend.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Cliente> cliente = repository.findByEmail(email);
        if (cliente == null) {
            throw new UsernameNotFoundException(email);
        } else {
            return new UserSpringSecurity(cliente.get().getId(), cliente.get().getEmail(), cliente.get().getSenha(), cliente.get().getPerfis());
        }
    }
}
