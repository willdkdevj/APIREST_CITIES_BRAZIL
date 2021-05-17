package br.com.supernova.citiesbrazil.service;

import br.com.supernova.citiesbrazil.exception.PatriarchateNotFoundException;
import br.com.supernova.citiesbrazil.model.Country;
import br.com.supernova.citiesbrazil.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository repository;

    public Page<Country> returnCatalogCountries(Pageable page) {
        return repository.findAll(page);
    }

    public ResponseEntity<Country> returnCountryName(String name) throws PatriarchateNotFoundException {
        Country country = checkedCountryByName(name);
        return ResponseEntity.ok(country);
    }

    public ResponseEntity<Country> returnCountryID(Long id) throws PatriarchateNotFoundException {
        Country country = repository.findById(id).orElseThrow(
                () -> new PatriarchateNotFoundException(id)
        );
        return ResponseEntity.ok(country);
    }

    private Country checkedCountryByName(String nameCountry) throws PatriarchateNotFoundException {
        return repository.findByName(nameCountry).orElseThrow(
                () -> new PatriarchateNotFoundException(nameCountry)
        );
    }
}
