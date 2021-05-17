package br.com.supernova.citiesbrazil.controller.resource;

import br.com.supernova.citiesbrazil.controller.implement.CountryController;
import br.com.supernova.citiesbrazil.exception.PatriarchateNotFoundException;
import br.com.supernova.citiesbrazil.model.Country;
import br.com.supernova.citiesbrazil.repository.CountryRepository;
import br.com.supernova.citiesbrazil.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/world")
@RequiredArgsConstructor
public class CountryResources implements CountryController {

    private final CountryService serviceCountry;

    @Override
    @GetMapping("/countries")
    public Page<Country> searchForCountries(final Pageable page) {
        return serviceCountry.returnCatalogCountries(page);
    }

    @Override
    @GetMapping("/country-by-name/{name}")
    public ResponseEntity<Country> searchCountryByName(@PathVariable String name) throws PatriarchateNotFoundException {
        return serviceCountry.returnCountryName(name);
    }

    @Override
    @GetMapping("/country-by-id/{id}")
    public ResponseEntity<Country> searchCountryByID(@PathVariable Long id) throws PatriarchateNotFoundException {
        return serviceCountry.returnCountryID(id);
    }
}
