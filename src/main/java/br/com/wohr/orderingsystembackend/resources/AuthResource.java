package br.com.wohr.orderingsystembackend.resources;

import br.com.wohr.orderingsystembackend.dto.EmailDTO;
import br.com.wohr.orderingsystembackend.security.JWTUtil;
import br.com.wohr.orderingsystembackend.security.UserSpringSecurity;
import br.com.wohr.orderingsystembackend.services.AuthService;
import br.com.wohr.orderingsystembackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthService service;

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSpringSecurity user = UserService.authenticated();
        String token = jwtUtil.generateToken(user.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("access-control-expose-headers", "Authorization");
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {

        String token = jwtUtil.generateToken(objDto.getEmail(), 120L);
        String url = getAppUrl(ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand().toUri());
        service.forgotPassword(objDto.getEmail(), token, url);

        return ResponseEntity.noContent().build();
    }

    private String getAppUrl(URI uri) {
        return uri.getScheme() + "://" + uri.getAuthority();
    }

}
