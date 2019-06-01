package com.eduardo.waes.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DIFF")
public class Diff {

    @Id
    private Long id;

    private String left;
    private String right;

    public Diff(Long id) {
        this.id = id;
    }
}
