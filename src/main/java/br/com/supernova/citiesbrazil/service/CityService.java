package br.com.supernova.citiesbrazil.service;

import br.com.supernova.citiesbrazil.exception.UrbeNotFoundException;
import br.com.supernova.citiesbrazil.model.City;
import br.com.supernova.citiesbrazil.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository repository;

    public Page<City> returnCatalogCities(Pageable page){
        return repository.findAll(page);
    }

    public City returnCityName(String name) throws UrbeNotFoundException {
        return repository.findByName(name).orElseThrow(
                () -> new UrbeNotFoundException(name)
        );
    }

    public City returnCityID(Long id) throws UrbeNotFoundException {
        return repository.findById(id).orElseThrow(
                () -> new UrbeNotFoundException(id)
        );
    }
}
