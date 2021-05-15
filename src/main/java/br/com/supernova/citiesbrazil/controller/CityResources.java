package br.com.supernova.citiesbrazil.controller;

import br.com.supernova.citiesbrazil.controller.implement.CityController;
import br.com.supernova.citiesbrazil.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CityResources implements CityController {
    @Override
    public Page<City> searchForCities(final Pageable page) {
        return serviceCountry.returnedCatalogCities();
    }

    @Override
    public Page searchCityByName(String name) {
        return null;
    }

    @Override
    public Page searchCityByID(Long id) {
        return null;
    }
}
