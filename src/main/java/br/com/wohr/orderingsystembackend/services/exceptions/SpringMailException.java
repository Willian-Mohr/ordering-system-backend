package br.com.wohr.orderingsystembackend.services.exceptions;


public class SpringMailException extends RuntimeException {

    public SpringMailException(String msg) {
        super(msg);
    }

}
