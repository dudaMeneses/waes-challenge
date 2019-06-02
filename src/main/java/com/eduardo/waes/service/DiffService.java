package com.eduardo.waes.service;

import com.eduardo.waes.domain.Diff;
import com.eduardo.waes.model.DirectionEnum;
import com.eduardo.waes.exception.DirectionAlreadyLoadedException;
import com.eduardo.waes.exception.DirectionNotLoadedException;
import com.eduardo.waes.model.DiffResult;
import com.eduardo.waes.model.DiffResultEnum;
import com.eduardo.waes.model.Difference;
import com.eduardo.waes.repository.DiffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <p>Business rules layer related to {@link Diff} entity. All the data processing and exceptions will come from here.</p>
 */
@Service
public class DiffService {

    @Autowired
    private DiffRepository diffRepository;

    /**
     *
     * <p>Store decoded value in the informed side</p>
     *
     * @param id unique identifier of {@link Diff} entity
     * @param value Base64 encoded value
     * @param direction possible direction where information will be saved
     * @throws DirectionAlreadyLoadedException
     *
     * @see DirectionEnum
     */
    @Transactional
    public void save(Long id, String value, DirectionEnum direction) throws DirectionAlreadyLoadedException {
        Diff diff = diffRepository.findById(id).orElse(new Diff(id));

        if(direction.equals(DirectionEnum.LEFT)){
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

    /**
     *
     * <p>Return the comparison result from both LEFT and RIGHT side to the identified {@link Diff}</p>
     * <p>In case sides are equal or have different sizes, it will be informed at the returning, but if they have same sizes the differences offset and length will be listed</p>
     *
     * @param id unique identifier of {@link Diff} entity
     * @return
     * @throws DirectionNotLoadedException
     * @see DiffResultEnum
     * @see DiffResult
     */
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

        return getResultWithDifferences(diff);
    }

    private DiffResult getResultWithDifferences(Diff diff) {
        DiffResult result = new DiffResult(DiffResultEnum.NOT_EQUALS);

        Integer offset = null;
        Integer length = 0;
        for(int i = 0 ; i < diff.getLeft().length() && i < diff.getRight().length() ; i++){
            if(diff.getLeft().charAt(i) != diff.getRight().charAt(i)){
                if(offset == null){
                    offset = i;
                }
                length++;
            } else {
                addDifferenceToResult(result, offset, length);
                offset = null;
                length = 0;
            }
        }

        addDifferenceToResult(result, offset, length);

        return result;
    }

    private void addDifferenceToResult(DiffResult result, Integer offset, Integer length) {
        if(offset != null){
            result.getDifferences().add(new Difference(offset, length));
        }
    }
}
