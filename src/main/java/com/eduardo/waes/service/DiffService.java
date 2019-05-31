package com.eduardo.waes.service;

import com.eduardo.waes.domain.Diff;
import com.eduardo.waes.domain.DirectionEnum;
import com.eduardo.waes.exception.DirectionAlreadyLoadedException;
import com.eduardo.waes.exception.DirectionNotLoadedException;
import com.eduardo.waes.model.DiffResult;
import com.eduardo.waes.model.DiffResultEnum;
import com.eduardo.waes.repository.DiffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class DiffService {

    @Autowired
    private DiffRepository diffRepository;

    public void save(Long id, String value, DirectionEnum direction) throws DirectionAlreadyLoadedException {
        Diff diff = diffRepository.findById(id).orElse(new Diff(id));

        if(direction.equals(DirectionEnum.left)){
            if(!StringUtils.isEmpty(diff.getLeft())){
                throw new DirectionAlreadyLoadedException();
            }

            diff.setLeft(new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8));
        } else {
            if(!StringUtils.isEmpty(diff.getRight())){
                throw new DirectionAlreadyLoadedException();
            }

            diff.setRight(new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8));
        }

        diffRepository.save(diff);
    }

    public DiffResult diff(Long id) throws DirectionNotLoadedException {
        Diff diff = diffRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if(StringUtils.isEmpty(diff.getLeft()) || StringUtils.isEmpty(diff.getRight())){
            throw new DirectionNotLoadedException();
        }

        if(diff.getLeft().equals(diff.getRight())){
            return new DiffResult(DiffResultEnum.EQUALS);
        }

        if(diff.getLeft().length() != diff.getRight().length()){
            return new DiffResult(DiffResultEnum.DIFFERENT_SIZE);
        }

        return null;
    }
}
