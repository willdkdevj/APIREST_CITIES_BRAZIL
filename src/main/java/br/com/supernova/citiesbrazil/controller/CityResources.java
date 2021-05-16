package br.com.supernova.citiesbrazil.controller;

import br.com.supernova.citiesbrazil.controller.implement.CityController;
import br.com.supernova.citiesbrazil.controller.implement.MessageResponse;
import br.com.supernova.citiesbrazil.enums.EarthRadius;
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
    @GetMapping("/city-by-name/{name}")
    public City searchCityByName(@PathVariable final String name) throws UrbeNotFoundException {
        return serviceCity.returnCityName(name);
    }

    @Override
    @GetMapping("/city-by-id/{id}")
    public City searchCityByID(@PathVariable final Long id) throws UrbeNotFoundException {
        return serviceCity.returnCityID(id);
    }

    @Override
    @GetMapping("/cities/distance-calculation")
    public MessageResponse calculateInMiles(@RequestParam(name = "from") final String city1,
                                            @RequestParam(name = "to") final String city2,
                                            @RequestParam(name = "radius") final EarthRadius radius) throws UrbeNotFoundException {
        return serviceCity.distanceByLocationByRadius(city1, city2, radius);
    }

    @Override
    @GetMapping("/cities/distance-in-miles")
    public MessageResponse distanceInMilesPostgre(@RequestParam(name = "from") final String city1,
                                                  @RequestParam(name = "to") final String city2) throws UrbeNotFoundException {
        return serviceCity.distanceByLocationInMilesPostgre(city1, city2);
    }

    @Override
    @GetMapping("/cities/distance-in-meters")
    public MessageResponse distanceInMetersPostgre(@RequestParam(name = "from") final String city1,
                                                   @RequestParam(name = "to") final String city2) throws UrbeNotFoundException {
        return serviceCity.distanceInMetersPostgre(city1, city2);
    }



}
