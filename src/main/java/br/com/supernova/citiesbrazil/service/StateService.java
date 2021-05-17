package br.com.supernova.citiesbrazil.service;

import br.com.supernova.citiesbrazil.exception.FederativeUnitNotFoundException;
import br.com.supernova.citiesbrazil.model.State;
import br.com.supernova.citiesbrazil.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository repository;

    public Page<State> returnCatalogStates(Pageable page) {
        return repository.findAll(page);
    }

    public ResponseEntity<State> returnStateName(String name) throws FederativeUnitNotFoundException {
        State state = repository.findByName(name).orElseThrow(
                () -> new FederativeUnitNotFoundException(name)
        );
        return ResponseEntity.ok(state);
    }

    public ResponseEntity<State> returnStateID(Long id) throws FederativeUnitNotFoundException {
        State state = repository.findById(id).orElseThrow(
                () -> new FederativeUnitNotFoundException(id)
        );
        return ResponseEntity.ok(state);
    }

    private State checkedStateByName(String nameState) throws FederativeUnitNotFoundException {
        return repository.findByName(nameState).orElseThrow(
                () -> new FederativeUnitNotFoundException(nameState)
        );
    }
}
