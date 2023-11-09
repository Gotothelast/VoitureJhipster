package fr.it_akademy_voiturejhipster.service.impl;

import fr.it_akademy_voiturejhipster.domain.Option;
import fr.it_akademy_voiturejhipster.repository.OptionRepository;
import fr.it_akademy_voiturejhipster.service.OptionService;
import fr.it_akademy_voiturejhipster.service.dto.OptionDTO;
import fr.it_akademy_voiturejhipster.service.mapper.OptionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy_voiturejhipster.domain.Option}.
 */
@Service
@Transactional
public class OptionServiceImpl implements OptionService {

    private final Logger log = LoggerFactory.getLogger(OptionServiceImpl.class);

    private final OptionRepository optionRepository;

    private final OptionMapper optionMapper;

    public OptionServiceImpl(OptionRepository optionRepository, OptionMapper optionMapper) {
        this.optionRepository = optionRepository;
        this.optionMapper = optionMapper;
    }

    @Override
    public OptionDTO save(OptionDTO optionDTO) {
        log.debug("Request to save Option : {}", optionDTO);
        Option option = optionMapper.toEntity(optionDTO);
        option = optionRepository.save(option);
        return optionMapper.toDto(option);
    }

    @Override
    public OptionDTO update(OptionDTO optionDTO) {
        log.debug("Request to update Option : {}", optionDTO);
        Option option = optionMapper.toEntity(optionDTO);
        option = optionRepository.save(option);
        return optionMapper.toDto(option);
    }

    @Override
    public Optional<OptionDTO> partialUpdate(OptionDTO optionDTO) {
        log.debug("Request to partially update Option : {}", optionDTO);

        return optionRepository
            .findById(optionDTO.getId())
            .map(existingOption -> {
                optionMapper.partialUpdate(existingOption, optionDTO);

                return existingOption;
            })
            .map(optionRepository::save)
            .map(optionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OptionDTO> findAll() {
        log.debug("Request to get all Options");
        return optionRepository.findAll().stream().map(optionMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OptionDTO> findOne(Long id) {
        log.debug("Request to get Option : {}", id);
        return optionRepository.findById(id).map(optionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Option : {}", id);
        optionRepository.deleteById(id);
    }
}
