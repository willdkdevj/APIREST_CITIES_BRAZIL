package br.com.supernova.citiesbrazil.controller.implement;

import br.com.supernova.citiesbrazil.exception.FederativeUnitNotFoundException;
import br.com.supernova.citiesbrazil.model.State;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Api("Interface for the Control of Brazilian States Management")
public interface StateController {

    @ApiOperation(value = "Operation to list states in management")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "States catalog successfully returned")
    })
    Page<State> searchForStates(Pageable page);

    @ApiOperation(value = "Operation to locate brazilian states by name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "State found successfully"),
            @ApiResponse(code = 404, message = "Could not find state reported")
    })
    ResponseEntity<State> searchStateByName(String name) throws FederativeUnitNotFoundException;

    @ApiOperation(value = "Operation to locate state by Database ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "State found successfully"),
            @ApiResponse(code = 404, message = "Could not find state reported")
    })
    ResponseEntity<State> searchStateByID(Long id) throws FederativeUnitNotFoundException;

}
