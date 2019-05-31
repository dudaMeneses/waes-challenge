package com.eduardo.waes.controller;

import com.eduardo.waes.domain.DirectionEnum;
import com.eduardo.waes.exception.DirectionAlreadyLoadedException;
import com.eduardo.waes.exception.DirectionNotLoadedException;
import com.eduardo.waes.model.DiffResult;
import com.eduardo.waes.service.DiffService;
import com.eduardo.waes.validation.IsBase64;
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

@Validated
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/v1/diff")
public class DiffController {

    @Autowired
    private DiffService diffService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/{id}/left")
    public void saveLeft(@PathVariable("id") Long id,
                     @Valid @NotBlank @IsBase64 @RequestBody String value) throws DirectionAlreadyLoadedException {
        diffService.save(id, value, DirectionEnum.left);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping(path = "/{id}/right")
    public void saveRight(@PathVariable("id") Long id,
                     @Valid @NotBlank @IsBase64 @RequestBody String value) throws DirectionAlreadyLoadedException {
        diffService.save(id, value, DirectionEnum.right);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DiffResult getDiff(@PathVariable("id") Long id) throws DirectionNotLoadedException {
        return diffService.diff(id);
    }
}
