package com.eduardo.waes.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@DBRider
@DataJpaTest
@RunWith(SpringRunner.class)
@DataSet("datasets/diff.yml")
public class DiffRepositoryTest {

    @Autowired
    private DiffRepository diffRepository;

    @Test
    public void whenFindById_shouldReturnTheEntity(){
        assertThat(diffRepository.findById(1L).orElse(null), allOf(
                hasProperty("id", equalTo(1L)),
                hasProperty("left", equalTo("test")),
                hasProperty("right", equalTo("test"))
        ));
    }

    @Test
    public void whenFindNothingById_shouldReturnOptionEmpty(){
        assertThat(diffRepository.findById(2L), is(equalTo(Optional.empty())));
    }
}