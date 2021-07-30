package br.com.wohr.orderingsystembackend.services;

import br.com.wohr.orderingsystembackend.security.UserSpringSecurity;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserServices {

    public static UserSpringSecurity authenticated() {
        try {
            return (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
