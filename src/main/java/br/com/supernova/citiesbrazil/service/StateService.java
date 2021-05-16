package br.com.supernova.citiesbrazil.service;

import br.com.supernova.citiesbrazil.exception.FederativeUnitNotFoundException;
import br.com.supernova.citiesbrazil.model.State;
import br.com.supernova.citiesbrazil.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository repository;

    public Page<State> returnCatalogStates(Pageable page) {
        return repository.findAll(page);
    }

    public State returnStateName(String name) throws FederativeUnitNotFoundException {
        return repository.findByName(name).orElseThrow(
                () -> new FederativeUnitNotFoundException(name)
        );
    }

    public State returnStateID(Long id) throws FederativeUnitNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new FederativeUnitNotFoundException(id)
        );
    }
}
