package com.eduardo.waes.controller;

import com.eduardo.waes.model.DirectionEnum;
import com.eduardo.waes.exception.DirectionAlreadyLoadedException;
import com.eduardo.waes.exception.DirectionNotLoadedException;
import com.eduardo.waes.model.DiffResult;
import com.eduardo.waes.service.DiffService;
import com.eduardo.waes.validation.Base64;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/diff")
@Api(description = "Diff API")
public class DiffController {

    @Autowired
    private DiffService diffService;

    @PostMapping(path = "/{id}/left")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Store LEFT value provided")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No content returned for successful savings to LEFT direction"),
            @ApiResponse(code = 400, message = "A bad request will be thrown in case of LEFT direction already exists to the informed ID or if value received is not a Base64 encoded string")
    })
    public void saveLeft(@PathVariable("id") Long id,
                         @Valid @NotNull @NotBlank @Base64 @RequestBody String value) throws DirectionAlreadyLoadedException {
        diffService.save(id, value, DirectionEnum.LEFT);
    }

    @PostMapping(path = "/{id}/right")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Store RIGHT value provided")
    @ApiResponses({
            @ApiResponse(code = 204, message = "No content returned for successful savings to RIGHT direction"),
            @ApiResponse(code = 400, message = "A bad request will be thrown in case of RIGHT direction already exists to the informed ID or if value received is not a Base64 encoded string")
    })
    public void saveRight(@PathVariable("id") Long id,
                          @Valid @NotNull @NotBlank @Base64 @RequestBody String value) throws DirectionAlreadyLoadedException {
        diffService.save(id, value, DirectionEnum.RIGHT);
    }

    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Diff RIGHT and LEFT values for the provided ID entity")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK returned for successful comparison between LEFT and RIGHT direction"),
            @ApiResponse(code = 400, message = "A bad request will be thrown in case of either LEFT or RIGHT directions are not loaded for the informed ID"),
            @ApiResponse(code = 404, message = "Entity not found will be thrown in case no entity exists for the informed ID")
    })
    public DiffResult getDiff(@PathVariable("id") Long id) throws DirectionNotLoadedException {
        return diffService.diff(id);
    }
}
