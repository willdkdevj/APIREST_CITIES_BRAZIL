package br.com.supernova.citiesbrazil.controller.implement;

import br.com.supernova.citiesbrazil.exception.UrbeNotFoundException;
import br.com.supernova.citiesbrazil.model.City;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api("Interface for the Control of Brazilian Cities Management")
public interface CityController {

    @ApiOperation(value = "Operation to list cities in management")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Brazilian Cities catalog successfully returned")
    })
    Page<City> searchForCities(Pageable page);

    @ApiOperation(value = "Operation to locate brazilian cities by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "City found successfully"),
            @ApiResponse(code = 404, message = "Could not find city reported")
    })
    City searchCityByName(String name) throws UrbeNotFoundException;

    @ApiOperation(value = "Operation to locate state by Database ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "State found successfully"),
            @ApiResponse(code = 404, message = "Could not find state reported")
    })
    City searchCityByID(Long id) throws UrbeNotFoundException;

    @ApiOperation(value = "Operation to calculate the distance (in miles) between two location points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Location Points returned successfully"),
            @ApiResponse(code = 404, message = "It was not possible to return the location with the parameters provided")
    })
    Page calculateInMiles(String city1, String city2) throws UrbeNotFoundException;

    @ApiOperation(value = "Operation to calculate the distance (in meters) between two location points")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Location Points returned successfully"),
            @ApiResponse(code = 404, message = "It was not possible to return the location with the parameters provided")
    })
    Page calculateInMeters(String city1, String city2) throws UrbeNotFoundException;
}
