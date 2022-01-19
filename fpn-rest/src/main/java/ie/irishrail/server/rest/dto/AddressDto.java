package ie.irishrail.server.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressDto {

    private Integer flat;

    private String  houseName;

    private Integer houseNumber;

    private String  street;

    private String  locality;

    private String  province;
}
