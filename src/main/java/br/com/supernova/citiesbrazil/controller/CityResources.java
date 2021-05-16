package br.com.supernova.citiesbrazil.controller;

import br.com.supernova.citiesbrazil.controller.implement.CityController;
import br.com.supernova.citiesbrazil.exception.UrbeNotFoundException;
import br.com.supernova.citiesbrazil.model.City;
import br.com.supernova.citiesbrazil.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/world/country/states")
@RequiredArgsConstructor
public class CityResources implements CityController {

    private final CityService serviceCity;

    @Override
    @GetMapping("/cities")
    public Page<City> searchForCities(final Pageable page) {
        return serviceCity.returnCatalogCities(page);
    }

    @Override
    @GetMapping("/city/{name}")
    public City searchCityByName(@PathVariable String name) throws UrbeNotFoundException {
        return serviceCity.returnCityName(name);
    }

    @Override
    @GetMapping("/city/{id}")
    public City searchCityByID(@PathVariable Long id) throws UrbeNotFoundException {
        return serviceCity.returnCityID(id);
    }

    @Override
    @GetMapping("/cities/distance-in-miles")
    public Page calculateInMiles(@RequestParam(name = "from") final String city1,
                                 @RequestParam(name = "to") final String city2) throws UrbeNotFoundException {
        return null; //serviceCity.distanceByLocationInMiles(city1, city2);
    }

    @Override
    @GetMapping("/cities/distance-in-meters")
    public Page calculateInMeters(@RequestParam(name = "from") final String city1,
                                  @RequestParam(name = "to") final String city2) throws UrbeNotFoundException {
        return null; // serviceCity.distanceByLocationInMeters(city1, city2);
    }
}
