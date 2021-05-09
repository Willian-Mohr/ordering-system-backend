package br.com.wohr.orderingsystembackend.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FildMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public List<FildMessage> getErrors() {
        return errors;
    }

    public void addError(String fildName, String message) {
        errors.add(new FildMessage(fildName, message));
    }
}
