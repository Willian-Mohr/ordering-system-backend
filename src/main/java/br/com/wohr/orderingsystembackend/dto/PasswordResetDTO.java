package br.com.wohr.orderingsystembackend.dto;

import java.io.Serializable;

public class PasswordResetDTO implements Serializable {

    private String senha;
    private String confirmaSenha;
    private String token;

    public PasswordResetDTO() {
    }

    public PasswordResetDTO(String senha, String confirmaSenha, String token) {
        this.senha = senha;
        this.confirmaSenha = confirmaSenha;
        this.token = token;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
