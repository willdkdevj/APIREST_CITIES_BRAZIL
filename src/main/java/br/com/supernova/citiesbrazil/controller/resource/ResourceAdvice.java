package br.com.supernova.citiesbrazil.controller.resource;

import br.com.supernova.citiesbrazil.exception.FederativeUnitNotFoundException;
import br.com.supernova.citiesbrazil.exception.PatriarchateNotFoundException;
import br.com.supernova.citiesbrazil.exception.UrbeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({
            FederativeUnitNotFoundException.class,
            PatriarchateNotFoundException.class,
            UrbeNotFoundException.class
    })
    public void notFound(){
        
    }
}
