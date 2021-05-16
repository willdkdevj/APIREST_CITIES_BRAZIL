package br.com.supernova.citiesbrazil.service;

import br.com.supernova.citiesbrazil.exception.PatriarchateNotFoundException;
import br.com.supernova.citiesbrazil.model.Country;
import br.com.supernova.citiesbrazil.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository repository;

    public Page<Country> returnCatalogCountries(Pageable page) {
        return repository.findAll(page);
    }

    public Country returnCountryName(String name) throws PatriarchateNotFoundException {
        return repository.findByName(name).orElseThrow(
                () -> new PatriarchateNotFoundException(name)
        );
    }

    public Country returnCountryID(Long id) throws PatriarchateNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new PatriarchateNotFoundException(id)
        );
    }
}
