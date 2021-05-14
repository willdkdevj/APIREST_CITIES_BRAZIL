package br.com.supernova.citiesbrazil.controller;

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
    Page searchCityByName(String name);

    @ApiOperation(value = "Operation to locate state by Database ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "State found successfully"),
            @ApiResponse(code = 404, message = "Could not find state reported")
    })
    Page searchCityByID(Long id);

}
