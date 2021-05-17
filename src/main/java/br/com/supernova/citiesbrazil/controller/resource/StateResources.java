package br.com.supernova.citiesbrazil.controller.resource;

import br.com.supernova.citiesbrazil.controller.implement.StateController;
import br.com.supernova.citiesbrazil.exception.FederativeUnitNotFoundException;
import br.com.supernova.citiesbrazil.model.State;
import br.com.supernova.citiesbrazil.service.StateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/world/country")
@RequiredArgsConstructor
public class StateResources implements StateController {

    private final StateService serviceState;

    @Override
    @GetMapping("/states")
    public Page<State> searchForStates(final Pageable page) {
        return serviceState.returnCatalogStates(page);
    }

    @Override
    @GetMapping("/state-by-name/{name}")
    public ResponseEntity<State> searchStateByName(@PathVariable String name) throws FederativeUnitNotFoundException {
        return serviceState.returnStateName(name);
    }

    @Override
    @GetMapping("/state-by-id/{id}")
    public ResponseEntity<State> searchStateByID(@PathVariable Long id) throws FederativeUnitNotFoundException {
        return serviceState.returnStateID(id);
    }
}
