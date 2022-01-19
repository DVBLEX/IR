package ie.irishrail.server.rest.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.irishrail.server.base.common.SecurityUtil;
import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.jsonentity.ApiResponseJsonEntity;
import ie.irishrail.server.base.jsonentity.SystemEnvironmentJson;
import ie.irishrail.server.rest.security.MyUserDetails;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping(value = "/system", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemController {

    @Value("${tc.system.name}")
    private String systemName;

    @Value("${tc.system.environment}")
    private String systemEnvironment;

    @Value("${tc.google.recaptcha.key.site}")
    private String recaptchaKeySite;

    @GetMapping
    public ApiResponseJsonEntity getEnvironmentData(HttpServletRequest request, HttpServletResponse response) throws FPNException {
        ApiResponseJsonEntity apiResponse = new ApiResponseJsonEntity();
        String responseSource = "fpnSystemEndpoint#";
        responseSource = responseSource + request.getRemoteAddr() + "#username=" + SecurityUtil.getSystemUsername();

        log.info(responseSource + "#Request: " + "[]");
        MyUserDetails authUser = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        SystemEnvironmentJson systemEnvironmentJson = new SystemEnvironmentJson();

        systemEnvironmentJson.setAppName(systemName);
        systemEnvironmentJson.setEnvironment(systemEnvironment);
        systemEnvironmentJson.setTestEnvironment(systemEnvironment.equals(ServerConstants.SYSTEM_ENVIRONMENT_LOCAL) || systemEnvironment.equals(
            ServerConstants.SYSTEM_ENVIRONMENT_DEV));
        systemEnvironmentJson.setUsername(authUser.getUsername());
        systemEnvironmentJson.setFirstName(authUser.getFirstname());
        systemEnvironmentJson.setLastName(authUser.getLastname());

        apiResponse.setSingleData(systemEnvironmentJson);
        apiResponse.setResponseCode(ServerResponseConstants.SUCCESS_CODE);
        apiResponse.setResponseText(ServerResponseConstants.SUCCESS_TEXT);
        apiResponse.setResponseDate(new Date());

        response.setStatus(HttpServletResponse.SC_OK);
        return apiResponse;
    }

    @GetMapping("/recaptchaKey")
    public String getRecaptchaKey() {

        return recaptchaKeySite;
    }

    @GetMapping("/health")
    public Map<String, String> returnStatus() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        return status;
    }
}
