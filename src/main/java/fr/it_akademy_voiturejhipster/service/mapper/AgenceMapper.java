package fr.it_akademy_voiturejhipster.service.mapper;

import fr.it_akademy_voiturejhipster.domain.Agence;
import fr.it_akademy_voiturejhipster.service.dto.AgenceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agence} and its DTO {@link AgenceDTO}.
 */
@Mapper(componentModel = "spring")
public interface AgenceMapper extends EntityMapper<AgenceDTO, Agence> {}
