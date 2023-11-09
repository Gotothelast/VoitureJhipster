package fr.it_akademy_voiturejhipster.service.impl;

import fr.it_akademy_voiturejhipster.domain.Mechanic;
import fr.it_akademy_voiturejhipster.repository.MechanicRepository;
import fr.it_akademy_voiturejhipster.service.MechanicService;
import fr.it_akademy_voiturejhipster.service.dto.MechanicDTO;
import fr.it_akademy_voiturejhipster.service.mapper.MechanicMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link fr.it_akademy_voiturejhipster.domain.Mechanic}.
 */
@Service
@Transactional
public class MechanicServiceImpl implements MechanicService {

    private final Logger log = LoggerFactory.getLogger(MechanicServiceImpl.class);

    private final MechanicRepository mechanicRepository;

    private final MechanicMapper mechanicMapper;

    public MechanicServiceImpl(MechanicRepository mechanicRepository, MechanicMapper mechanicMapper) {
        this.mechanicRepository = mechanicRepository;
        this.mechanicMapper = mechanicMapper;
    }

    @Override
    public MechanicDTO save(MechanicDTO mechanicDTO) {
        log.debug("Request to save Mechanic : {}", mechanicDTO);
        Mechanic mechanic = mechanicMapper.toEntity(mechanicDTO);
        mechanic = mechanicRepository.save(mechanic);
        return mechanicMapper.toDto(mechanic);
    }

    @Override
    public MechanicDTO update(MechanicDTO mechanicDTO) {
        log.debug("Request to update Mechanic : {}", mechanicDTO);
        Mechanic mechanic = mechanicMapper.toEntity(mechanicDTO);
        mechanic = mechanicRepository.save(mechanic);
        return mechanicMapper.toDto(mechanic);
    }

    @Override
    public Optional<MechanicDTO> partialUpdate(MechanicDTO mechanicDTO) {
        log.debug("Request to partially update Mechanic : {}", mechanicDTO);

        return mechanicRepository
            .findById(mechanicDTO.getId())
            .map(existingMechanic -> {
                mechanicMapper.partialUpdate(existingMechanic, mechanicDTO);

                return existingMechanic;
            })
            .map(mechanicRepository::save)
            .map(mechanicMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MechanicDTO> findAll() {
        log.debug("Request to get all Mechanics");
        return mechanicRepository.findAll().stream().map(mechanicMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the mechanics where Car is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MechanicDTO> findAllWhereCarIsNull() {
        log.debug("Request to get all mechanics where Car is null");
        return StreamSupport
            .stream(mechanicRepository.findAll().spliterator(), false)
            .filter(mechanic -> mechanic.getCar() == null)
            .map(mechanicMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MechanicDTO> findOne(Long id) {
        log.debug("Request to get Mechanic : {}", id);
        return mechanicRepository.findById(id).map(mechanicMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mechanic : {}", id);
        mechanicRepository.deleteById(id);
    }
}
