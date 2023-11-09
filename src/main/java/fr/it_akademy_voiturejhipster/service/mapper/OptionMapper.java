package fr.it_akademy_voiturejhipster.service.mapper;

import fr.it_akademy_voiturejhipster.domain.Car;
import fr.it_akademy_voiturejhipster.domain.Option;
import fr.it_akademy_voiturejhipster.service.dto.CarDTO;
import fr.it_akademy_voiturejhipster.service.dto.OptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Option} and its DTO {@link OptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface OptionMapper extends EntityMapper<OptionDTO, Option> {
    @Mapping(target = "car", source = "car", qualifiedByName = "carId")
    OptionDTO toDto(Option s);

    @Named("carId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CarDTO toDtoCarId(Car car);
}
