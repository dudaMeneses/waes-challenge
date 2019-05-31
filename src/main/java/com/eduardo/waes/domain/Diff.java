package com.eduardo.waes.domain;

import com.eduardo.waes.validation.IsBase64;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Diff {

    @Id
    private Long id;

    @IsBase64
    private String left;

    @IsBase64
    private String right;

    public Diff(Long id) {
        this.id = id;
    }
}
