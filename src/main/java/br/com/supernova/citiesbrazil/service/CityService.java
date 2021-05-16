package br.com.supernova.citiesbrazil.service;

import br.com.supernova.citiesbrazil.controller.MessageResponse;
import br.com.supernova.citiesbrazil.enums.EarthRadius;
import br.com.supernova.citiesbrazil.exception.UrbeNotFoundException;
import br.com.supernova.citiesbrazil.model.City;
import br.com.supernova.citiesbrazil.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

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

    public MessageResponse distanceByLocationByRadius(String city1, String city2, EarthRadius radius)
            throws UrbeNotFoundException {
        City foundCity1 = returnCityName(city1);
        City foundCity2 = returnCityName(city2);

        List<City> cities = repository.findAllById(Arrays.asList(foundCity1.getId(),
                                                                 foundCity2.getId()));

        Double[] location1 = convertGeo(cities.get(0).getGeolocation());
        Double[] location2 = convertGeo(cities.get(1).getGeolocation());

        Double obtainedDistance = calculateDistance(location1[0],
                                                    location1[1],
                                                    location2[0],
                                                    location2[1], radius);

        MessageResponse message = createMessageResponse(obtainedDistance, "The distance between the two points is: ");
        message.setMessage(message.getMessage() + " " + radius.getUnit());

        return message;
    }

    public MessageResponse distanceByLocationInMilesPostgre(String city1, String city2) throws UrbeNotFoundException {
        City foundCity1 = returnCityName(city1);
        City foundCity2 = returnCityName(city2);

        Double obtainedDistance = repository.distanceByPoints(foundCity1.getId(), foundCity2.getId());

        return createMessageResponse(obtainedDistance, "The distance in miles obtained by PostgreSQL between the two points is: ");
    }

    public MessageResponse distanceInMetersPostgre(String city1, String city2) throws UrbeNotFoundException {
        City foundCity1 = returnCityName(city1);
        City foundCity2 = returnCityName(city2);

        Point point1 = foundCity1.getLocation();
        Point point2 = foundCity2.getLocation();

        Double obtainedDistance = repository.distanceByCube(point1.getX(), point1.getY(),
                                                            point2.getX(), point2.getY());

        return createMessageResponse(obtainedDistance, "The distance in meters obtained by PostgreSQL between the two points is: ");
    }

    private MessageResponse createMessageResponse(Double value, String message){
        return MessageResponse.builder()
                .message(message + value)
                .build();
    }

    private Double[] convertGeo(String value){
        String obtainedString = value.replace("(", "")
                                     .replace(")", "");
        String[] values = obtainedString.trim().split(",");
        return new Double[] {Double.valueOf(values[0]), Double.valueOf(values[1])};
    }

    private Double calculateDistance(Double latitude1, Double longitude1,
                                     Double latitude2, Double longitude2, EarthRadius radius){
        Double differenceBetweenLatitudes = toRadians(latitude2 - latitude1);
        Double differenceBetweenLongitudes = toRadians(longitude2 - longitude1);
        Double obtainedArea = sin(differenceBetweenLatitudes / 2) * sin(differenceBetweenLatitudes / 2) +
                              cos(toRadians(latitude1)) * cos(toRadians(latitude2)) * sin(differenceBetweenLongitudes /2) * sin(differenceBetweenLongitudes / 2);
        Double factor = 2 * atan2(sqrt(obtainedArea), sqrt(1 - obtainedArea));

        return radius.getValue() * factor;
    }
}
