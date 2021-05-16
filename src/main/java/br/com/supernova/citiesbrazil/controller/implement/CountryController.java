package br.com.supernova.citiesbrazil.controller.implement;

import br.com.supernova.citiesbrazil.exception.PatriarchateNotFoundException;
import br.com.supernova.citiesbrazil.model.Country;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Api("Interface for the Control of Countries Management")
public interface CountryController {

    @ApiOperation(value = "Operation to list countries in management")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Countries catalog successfully returned")
    })
    Page<Country> searchForCountries(Pageable page);

    @ApiOperation(value = "Operation to locate country by international name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Country found successfully"),
            @ApiResponse(code = 404, message = "Could not find country reported")
    })
    Country searchCountryByName(String name) throws PatriarchateNotFoundException;

    @ApiOperation(value = "Operation to locate country by Database ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Country found successfully"),
            @ApiResponse(code = 404, message = "Could not find country reported")
    })
    Country searchCountryByID(Long id) throws PatriarchateNotFoundException;

}
