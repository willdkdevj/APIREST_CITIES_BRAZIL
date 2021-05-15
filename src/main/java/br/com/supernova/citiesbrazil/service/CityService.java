package br.com.supernova.citiesbrazil.service;

import br.com.supernova.citiesbrazil.model.City;
import br.com.supernova.citiesbrazil.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository repository;

    public Page<City> returnCatalogCities(Pageable page){
        return repository.findAll(page);
    }

    public Page returnCityName(String name){
        // Page<City> cityPage = new Page<City>(repository.findByName(name));
        return null; //cityPage;
    }
}
