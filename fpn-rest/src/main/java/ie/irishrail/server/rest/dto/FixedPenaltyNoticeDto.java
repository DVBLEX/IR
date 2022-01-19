package ie.irishrail.server.rest.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixedPenaltyNoticeDto {

    private Integer    fpnNumber;

    private Integer    rnNumber;

    private String     firstName;

    private String     surname;

    private AddressDto address;

    private BigDecimal chargeAmount;

    private Boolean    isAppealSubmitted;

    private OffenceDto offence;
}
