package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.domain.Cidade;
import br.com.wohr.orderingsystembackend.domain.Cliente;
import br.com.wohr.orderingsystembackend.domain.Endereco;
import br.com.wohr.orderingsystembackend.domain.enums.Perfil;
import br.com.wohr.orderingsystembackend.domain.enums.TipoCliente;
import br.com.wohr.orderingsystembackend.dto.ClienteDTO;
import br.com.wohr.orderingsystembackend.dto.ClienteNewDTO;
import br.com.wohr.orderingsystembackend.dto.PasswordResetDTO;
import br.com.wohr.orderingsystembackend.repositories.ClienteRepository;
import br.com.wohr.orderingsystembackend.repositories.EnderecoRepository;
import br.com.wohr.orderingsystembackend.security.UserSpringSecurity;
import br.com.wohr.orderingsystembackend.services.exceptions.AuthorizationException;
import br.com.wohr.orderingsystembackend.services.exceptions.DataIntegrityException;
import br.com.wohr.orderingsystembackend.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ClienteRepository repo;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Cliente find(Integer id) {

        UserSpringSecurity user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto n??o encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    public Cliente findByEmail(String email) {

        UserSpringSecurity user = UserService.authenticated();
        if (user == null || !user.hasRole(Perfil.ADMIN) && !email.equals(user.getUsername())) {
            throw new AuthorizationException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findByEmail(email);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto n??o encontrado! Email: " + email + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj.setId(null);
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("N??o ?? poss??vel excluir porque h?? pedidos relacionadas");
        }
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDto(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
    }

    public Cliente fromDto(ClienteNewDTO objDto) {
        Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), passwordEncoder.encode(objDto.getSenha()));
        Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().addAll(Arrays.asList(objDto.getTelefone1(), objDto.getTelefone2(), objDto.getTelefone3()));
        if (objDto.getTelefone2() != null) {
            cli.getTelefones().add(objDto.getTelefone2());
        }
        if (objDto.getTelefone3() != null) {
            cli.getTelefones().add(objDto.getTelefone3());
        }
        return cli;
    }

    public Cliente updatePassword(Cliente cliente, PasswordResetDTO objDto) {

        if (!objDto.getSenha().equals(objDto.getConfirmaSenha())) {
            throw new IllegalStateException();
        }
        cliente.setSenha(passwordEncoder.encode(objDto.getSenha()));
        return repo.save(cliente);
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {

        UserSpringSecurity user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }

}
