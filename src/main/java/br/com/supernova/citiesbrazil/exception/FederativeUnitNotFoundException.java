package br.com.supernova.citiesbrazil.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FederativeUnitNotFoundException extends Exception{

    public FederativeUnitNotFoundException(String name) {
        super("State not found with name provided: " + name);
    }

    public FederativeUnitNotFoundException(Long id) {
        super("State not found with the Id - " + id);
    }
}
