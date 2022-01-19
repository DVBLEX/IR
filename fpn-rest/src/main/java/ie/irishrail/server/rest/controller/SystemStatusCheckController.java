package ie.irishrail.server.rest.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.exception.FPNException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.web.bind.annotation.RestController;

@CommonsLog
@RestController
@RequestMapping(value = "/system/check.htm")
public class SystemStatusCheckController {

    @Value("${system.check.client.id}")
    private String       systemCheckClientId;

    @Value("${system.check.client.secret}")
    private String       systemCheckClientSecret;

    @Value("${system.check.referer.header}")
    private String       systemCheckRefererHeader;

    @Value("${system.check.user.agent.header}")
    private String       systemCheckUserAgentHeader;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping
    public @ResponseBody String processCheckRequest(HttpServletRequest request, @RequestParam("actions") String actions) throws FPNException {

        String responseSource = "CheckController#";

        String userAgentHeader = request.getHeader("User-Agent");
        String refererHeader = request.getHeader("Referer");
        String authHeader = request.getHeader("Auth");

        StringBuilder builder = new StringBuilder();
        builder.append(responseSource);
        builder.append("Request: ");
        builder.append(request.getRemoteAddr());
        builder.append("#");
        builder.append("[actions=");
        builder.append(actions);
        builder.append("]# headers");
        builder.append("[authorization=");
        builder.append(request.getHeader("Auth"));
        builder.append(", refererHeader=");
        builder.append(refererHeader);
        builder.append(", userAgent=");
        builder.append(userAgentHeader);
        builder.append("]");

        log.info(builder.toString());

        final StringBuilder response = new StringBuilder();

        try {
            if (StringUtils.isBlank(userAgentHeader))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#1");

            if (StringUtils.isBlank(refererHeader))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#2");

            if (StringUtils.isBlank(authHeader) || authHeader.length() < 8)
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#3");

            if (!userAgentHeader.equals(systemCheckUserAgentHeader))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#4");

            if (!refererHeader.equals(systemCheckRefererHeader))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#5");

            authHeader = authHeader.substring(6);

            authHeader = new String(Base64Utils.decode(authHeader.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

            if (!authHeader.contains(":") || authHeader.length() <= authHeader.indexOf(":"))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#6");

            String requestClientId = authHeader.substring(0, authHeader.indexOf(":"));
            String requestClientSecret = authHeader.substring(authHeader.indexOf(":") + 1);

            if (StringUtils.isBlank(requestClientId))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#7");

            if (StringUtils.isBlank(requestClientSecret))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#8");

            if (!requestClientId.equals(systemCheckClientId))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#9");

            if (!requestClientSecret.equals(systemCheckClientSecret))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#10");

            if (!actions.matches(ServerConstants.REGEX_ACTIONS))
                throw new FPNException(ServerResponseConstants.API_AUTHENTICATION_FAILURE_CODE, ServerResponseConstants.API_AUTHENTICATION_FAILURE_TEXT, "#11");

            final List<Boolean> isAlertList = new ArrayList<>();
            isAlertList.add(0, false);
            try {
                jdbcTemplate.query("SELECT id, comparison_result FROM system_checks WHERE id IN (" + actions + ")", rs -> {

                    isAlertList.set(0, (isAlertList.get(0) || rs.getBoolean("comparison_result")));
                });
            } catch (Exception e) {
                log.error(responseSource + "##Exception[query]: ", e);
                isAlertList.set(0, true);
            }

            if (isAlertList.get(0)) {
                response.append("ResponseCode=");
                response.append(ServerResponseConstants.FAILURE_CODE);
                response.append(", ResponseText=");
                response.append(ServerResponseConstants.FAILURE_TEXT);
            } else {
                response.append("ResponseCode=");
                response.append(ServerResponseConstants.SUCCESS_CODE);
                response.append(", ResponseText=");
                response.append(ServerResponseConstants.SUCCESS_TEXT);
            }
        } catch (FPNException fpne) {
            response.append(", ResponseCode=");
            response.append(fpne.getResponseCode());
            response.append(", ResponseText=");
            response.append(fpne.getResponseText());
            response.append(", ResponseSource=");
            response.append(fpne.getResponseSource());

        } catch (Exception e) {
            log.error(responseSource + "##Exception: ", e);

            response.append(", ResponseCode=");
            response.append(ServerResponseConstants.API_FAILURE_CODE);
            response.append(", ResponseText=");
            response.append(ServerResponseConstants.API_FAILURE_TEXT);
        }

        builder = new StringBuilder();
        builder.append(responseSource);
        builder.append("Response: ");
        builder.append("[actions=");
        builder.append(actions);
        builder.append(", ");
        builder.append(response);
        builder.append("]");
        log.info(builder.toString());

        return response.toString();
    }
}
