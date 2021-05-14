package br.com.supernova.citiesbrazil.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UrbeNotFoundException extends Exception{

    public UrbeNotFoundException(String name) {
        super("City not found with name provided: " + name);
    }

    public UrbeNotFoundException(Long id) {
        super("City not found with the Id - " + id);
    }
}
