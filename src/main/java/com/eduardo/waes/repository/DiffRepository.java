package com.eduardo.waes.repository;

import com.eduardo.waes.domain.Diff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * <p>Repository layer for {@link Diff} entity. Extends {@link CrudRepository} to reuse JPA save actions</p>
 */
@Repository
public interface DiffRepository extends CrudRepository<Diff, Long> {

    /**
     * <p>Find entity by its id</p>
     * @param id {@link Diff} unique identifier
     * @return
     */
    @Query("select d from Diff d where d.id = :id")
    Optional<Diff> findById(@Param("id") Long id);
}
