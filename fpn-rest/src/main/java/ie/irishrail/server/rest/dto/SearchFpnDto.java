package ie.irishrail.server.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchFpnDto {
    @JsonProperty
    @NotNull(message = "FPN Number may not be null")
    private Integer fpnNumber;

    @JsonProperty
    @NotNull(message = "RN Number may not be null")
    private Integer rnNumber;
}
