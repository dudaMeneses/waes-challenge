package com.eduardo.waes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiffResult {

    @NonNull
    private DiffResultEnum result;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Set<Difference> differences = new HashSet<>();

}
