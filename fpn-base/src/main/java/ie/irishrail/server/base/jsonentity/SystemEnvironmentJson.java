package ie.irishrail.server.base.jsonentity;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class SystemEnvironmentJson {

    private String  appName;
    private String  environment;
    private boolean isTestEnvironment;
    private String  username;
    private String  firstName;
    private String  lastName;
    private String  recaptchaKey;
    private String  userMobileNumber;
    private String  userEmail;
}
