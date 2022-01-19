package ie.irishrail.server.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ie.irishrail.server.base.entity.Offence;
import ie.irishrail.server.rest.dto.OffenceDto;

@Mapper
public interface OffenceMapper {

    OffenceMapper MAPPER = Mappers.getMapper(OffenceMapper.class);

    @Mappings({})
    OffenceDto toDto(Offence offence);
}
