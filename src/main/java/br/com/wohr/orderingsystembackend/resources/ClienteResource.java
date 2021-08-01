package br.com.wohr.orderingsystembackend.resources;

import br.com.wohr.orderingsystembackend.domain.Cliente;
import br.com.wohr.orderingsystembackend.dto.ClienteDTO;
import br.com.wohr.orderingsystembackend.dto.ClienteNewDTO;
import br.com.wohr.orderingsystembackend.dto.PasswordResetDTO;
import br.com.wohr.orderingsystembackend.security.JWTUtil;
import br.com.wohr.orderingsystembackend.services.ClienteService;
import br.com.wohr.orderingsystembackend.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {

        Cliente obj = service.find(id);

        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
        Cliente obj = service.fromDto(objDto);
        service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
        Cliente obj = service.fromDto(objDto);
        obj.setId(id);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ClienteDTO>> findAll() {

        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public ResponseEntity<Page<ClienteDTO>> findAll(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction) {

        Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));

        return ResponseEntity.ok().body(listDto);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public ModelAndView showChangePasswordPage(@RequestParam("Authorization") String token) {

        if (!jwtUtil.tokenValido(token)) {
            throw new AuthorizationException("Não autorizado");
        } else {
            String user = jwtUtil.getUsername(token);
            Cliente cliente = service.findByEmail(jwtUtil.getUsername(token));
            return new ModelAndView("passwordReset/updatePassword").addObject("token", token);
        }
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public ResponseEntity<Void> updateTeste(@RequestBody PasswordResetDTO objDto, @RequestParam("token") String token) {

        if (!jwtUtil.tokenValido(token)) {
            throw new AuthorizationException("Não autorizado");
        } else {
            String user = jwtUtil.getUsername(token);
            Cliente cliente = service.findByEmail(jwtUtil.getUsername(token));
            service.updatePassword(cliente, objDto);
        }
        return ResponseEntity.noContent().build();
    }

}