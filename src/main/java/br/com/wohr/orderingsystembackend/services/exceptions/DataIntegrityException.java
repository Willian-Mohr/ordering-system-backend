package br.com.wohr.orderingsystembackend.services.exceptions;


public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String msg) {
        super(msg);
    }

}
