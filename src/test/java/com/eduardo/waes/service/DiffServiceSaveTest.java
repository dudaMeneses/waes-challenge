package com.eduardo.waes.service;

import com.eduardo.waes.domain.Diff;
import com.eduardo.waes.domain.DirectionEnum;
import com.eduardo.waes.exception.DirectionAlreadyLoadedException;
import com.eduardo.waes.repository.DiffRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DiffServiceSaveTest {

    @Spy
    @InjectMocks
    private DiffService diffService;

    @Mock
    private DiffRepository diffRepository;

    @Test
    public void whenDiffNotFound_shouldCreateDiff() throws DirectionAlreadyLoadedException {
        Diff diff = new Diff(123L, "test", null);

        doReturn(Optional.empty()).when(diffRepository).findById(123L);

        diffService.save(123L, "test", DirectionEnum.left);

        verify(diffRepository, times(1)).save(diff);
    }

    @Test(expected = DirectionAlreadyLoadedException.class)
    public void whenDiffFoundButAlreadyHaveValueOnLeftDirection_shouldThrowDirectionAlreadyLoadedException() throws DirectionAlreadyLoadedException {
        Diff diff = new Diff(123L, "test", null);

        doReturn(Optional.of(diff)).when(diffRepository).findById(123L);

        diffService.save(123L, "test", DirectionEnum.left);
    }

    @Test(expected = DirectionAlreadyLoadedException.class)
    public void whenDiffFoundButAlreadyHaveValueOnRightDirection_shouldThrowDirectionAlreadyLoadedException() throws DirectionAlreadyLoadedException {
        Diff diff = new Diff(123L, null, "test");

        doReturn(Optional.of(diff)).when(diffRepository).findById(123L);

        diffService.save(123L, "test", DirectionEnum.right);
    }

    @Test
    public void whenDiffFound_shouldAddValueToDiff() throws DirectionAlreadyLoadedException {
        Diff diff = new Diff(123L, null, "test");

        doReturn(Optional.of(diff)).when(diffRepository).findById(123L);

        diffService.save(123L, "test", DirectionEnum.left);

        verify(diffRepository, times(1)).save(new Diff(123L, "test", "test"));
    }

}