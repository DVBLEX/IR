package ie.irishrail.server.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import ie.irishrail.server.base.entity.FixedPenaltyNotice;
import ie.irishrail.server.rest.dto.AddressDto;

@Mapper
public interface AddressMapper {

    AddressMapper MAPPER = Mappers.getMapper(AddressMapper.class);

    @Mappings({})
    AddressDto toDto(FixedPenaltyNotice fixedPenaltyNotice);
}
