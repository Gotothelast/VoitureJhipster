package fr.it_akademy_voiturejhipster.service.mapper;

import fr.it_akademy_voiturejhipster.domain.Car;
import fr.it_akademy_voiturejhipster.domain.Mechanic;
import fr.it_akademy_voiturejhipster.service.dto.CarDTO;
import fr.it_akademy_voiturejhipster.service.dto.MechanicDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Car} and its DTO {@link CarDTO}.
 */
@Mapper(componentModel = "spring")
public interface CarMapper extends EntityMapper<CarDTO, Car> {
    @Mapping(target = "mechanic", source = "mechanic", qualifiedByName = "mechanicId")
    CarDTO toDto(Car s);

    @Named("mechanicId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MechanicDTO toDtoMechanicId(Mechanic mechanic);
}
