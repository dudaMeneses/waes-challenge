package com.eduardo.waes.service;

import com.eduardo.waes.domain.Diff;
import com.eduardo.waes.exception.DirectionNotLoadedException;
import com.eduardo.waes.repository.DiffRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

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
        doReturn(new Diff(123L, "test", null)).when(diffRepository).findById(123L);
        diffService.diff(123L);
    }

    @Test(expected = DirectionNotLoadedException.class)
    public void whenLeftIsEmpty_shouldThrowDirectionNotLoadedException() throws DirectionNotLoadedException {
        doReturn(new Diff(123L, null, "test")).when(diffRepository).findById(123L);
        diffService.diff(123L);
    }
}
