package com.eduardo.waes.repository;

import com.eduardo.waes.domain.Diff;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class DiffRepository {

    private Map<Long, Diff> database;

    public Optional<Diff> findById(Long id) {
        return Optional.empty();
    }

    public void save(Diff diff) {
    }
}
