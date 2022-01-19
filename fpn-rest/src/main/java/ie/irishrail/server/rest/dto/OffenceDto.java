package ie.irishrail.server.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class OffenceDto {

    private String title;

    private String description;

    private String appealLetterText;
}
