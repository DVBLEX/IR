package ie.irishrail.server.rest.mappers;

import ie.irishrail.server.base.entity.FixedPenaltyNotice;
import ie.irishrail.server.rest.dto.FixedPenaltyNoticeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FixedPenaltyNoticeMapper {

    FixedPenaltyNoticeMapper MAPPER = Mappers.getMapper(FixedPenaltyNoticeMapper.class);

    @Mappings({})
    FixedPenaltyNoticeDto toDto(FixedPenaltyNotice fixedPenaltyNotice);
}
