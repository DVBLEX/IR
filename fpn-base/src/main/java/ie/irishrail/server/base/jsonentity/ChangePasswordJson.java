package ie.irishrail.server.base.jsonentity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ChangePasswordJson {

    @ToString.Exclude
    private String oldPassword;
    @ToString.Exclude
    private String newPassword;
    @ToString.Exclude
    private String confirmPassword;

}
