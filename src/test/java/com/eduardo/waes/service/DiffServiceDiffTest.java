package com.eduardo.waes.service;

import com.eduardo.waes.domain.Diff;
import com.eduardo.waes.exception.DirectionNotLoadedException;
import com.eduardo.waes.model.DiffResult;
import com.eduardo.waes.model.DiffResultEnum;
import com.eduardo.waes.model.Difference;
import com.eduardo.waes.repository.DiffRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceDiffTest {

    @Spy
    @InjectMocks
    private DiffService diffService;

    @Mock
    private DiffRepository diffRepository;

    @Test(expected = EntityNotFoundException.class)
    public void whenNoDiffFound_shouldThrowEntityNotFoundException() throws DirectionNotLoadedException {
        diffService.diff(123L);
    }

    @Test(expected = DirectionNotLoadedException.class)
    public void whenRightIsEmpty_shouldThrowDirectionNotLoadedException() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, "test", null))).when(diffRepository).findById(123L);
        diffService.diff(123L);
    }

    @Test(expected = DirectionNotLoadedException.class)
    public void whenLeftIsEmpty_shouldThrowDirectionNotLoadedException() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, null, "test"))).when(diffRepository).findById(123L);
        diffService.diff(123L);
    }

    @Test
    public void whenBothSidesAreEquals_shouldReturnEquals() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, "test", "test"))).when(diffRepository).findById(123L);
        DiffResult result = diffService.diff(123L);
        assertThat(result, hasProperty("result", equalTo(DiffResultEnum.EQUALS)));
    }

    @Test
    public void whenSidesHaveDifferentSizes_shouldReturnDifferentSizes() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, "testing", "test"))).when(diffRepository).findById(123L);
        DiffResult result = diffService.diff(123L);
        assertThat(result, hasProperty("result", equalTo(DiffResultEnum.DIFFERENT_SIZE)));
    }

    @Test
    public void whenSidesHaveSameSizeButAreDifferentInLastChar_shouldReturnNotEqualsAndTheDifference() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, "test1", "test2"))).when(diffRepository).findById(123L);
        DiffResult result = diffService.diff(123L);
        assertThat(result, allOf(
                hasProperty("result", equalTo(DiffResultEnum.NOT_EQUALS)),
                hasProperty("differences", hasItem(allOf(
                        hasProperty("offset", equalTo(4)),
                        hasProperty("length", equalTo(1))
                ))
        )));
    }

    @Test
    public void whenSidesHaveSameSizeButAreDifferentInTheMiddle_shouldReturnNotEqualsAndTheDifference() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, "Americas", "Amateurs"))).when(diffRepository).findById(123L);
        DiffResult result = diffService.diff(123L);
        assertThat(result, allOf(
                hasProperty("result", equalTo(DiffResultEnum.NOT_EQUALS)),
                hasProperty("differences", hasItem(allOf(
                        hasProperty("offset", equalTo(2)),
                        hasProperty("length", equalTo(5))
                        ))
                )));
    }

    @Test
    public void whenSidesHaveSameSizeButAreDifferentInTheBeginning_shouldReturnNotEqualsAndTheDifference() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, "Test", "Fest"))).when(diffRepository).findById(123L);
        DiffResult result = diffService.diff(123L);
        assertThat(result, allOf(
                hasProperty("result", equalTo(DiffResultEnum.NOT_EQUALS)),
                hasProperty("differences", hasItem(allOf(
                        hasProperty("offset", equalTo(0)),
                        hasProperty("length", equalTo(1))
                        ))
                )));
    }

    @Test
    public void whenSidesHaveSameSizeButAreDifferentInManyPoints_shouldReturnNotEqualsAndTheDifference() throws DirectionNotLoadedException {
        doReturn(Optional.of(new Diff(123L, "Roma is in Europe", "Rome is in Italia"))).when(diffRepository).findById(123L);
        DiffResult result = diffService.diff(123L);
        assertThat(result, allOf(
                hasProperty("result", equalTo(DiffResultEnum.NOT_EQUALS)),
                hasProperty("differences", hasItems(
                        new Difference(3, 1),
                        new Difference(11, 6)
                ))
        ));
    }
}
