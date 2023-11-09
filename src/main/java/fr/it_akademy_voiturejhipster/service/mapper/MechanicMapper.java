package fr.it_akademy_voiturejhipster.service.mapper;

import fr.it_akademy_voiturejhipster.domain.Mechanic;
import fr.it_akademy_voiturejhipster.service.dto.MechanicDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mechanic} and its DTO {@link MechanicDTO}.
 */
@Mapper(componentModel = "spring")
public interface MechanicMapper extends EntityMapper<MechanicDTO, Mechanic> {}
