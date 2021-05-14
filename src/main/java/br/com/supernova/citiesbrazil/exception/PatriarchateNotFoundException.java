package br.com.supernova.citiesbrazil.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatriarchateNotFoundException extends Exception{

    public PatriarchateNotFoundException(String name) {
        super("Country not found with name provided: " + name);
    }

    public PatriarchateNotFoundException(Long id) {
        super("Country not found with the Id - " + id);
    }
}
