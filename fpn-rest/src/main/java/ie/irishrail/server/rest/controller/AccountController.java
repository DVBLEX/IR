package ie.irishrail.server.rest.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.irishrail.server.base.common.SecurityUtil;
import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.entity.Account;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.exception.FPNValidationException;
import ie.irishrail.server.base.jsonentity.AccountJson;
import ie.irishrail.server.base.jsonentity.ApiResponseJsonEntity;
import ie.irishrail.server.base.service.AccountService;

@RestController
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {

    private PasswordEncoder passwordEncoder;
    private AccountService  accountService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/password/change", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponseJsonEntity changePassword(HttpServletResponse response, @RequestBody AccountJson accountJson) throws FPNException, FPNValidationException {

        ApiResponseJsonEntity apiResponse = new ApiResponseJsonEntity();

        Account loggedAccount = accountService.getActiveAccountByUsername(SecurityUtil.getSystemUsername());

        if (loggedAccount == null)
            throw new FPNException(ServerResponseConstants.API_FAILURE_CODE, ServerResponseConstants.API_FAILURE_TEXT, "AccountController#changePassword#loggedAccountIsNull");
        else if (!passwordEncoder.matches(accountJson.getCurrentPassword(), loggedAccount.getPassword()))
            throw new FPNValidationException(ServerResponseConstants.INVALID_CURRENT_PASSWORD_CODE, ServerResponseConstants.INVALID_CURRENT_PASSWORD_TEXT,
                "Mismatch#CurrentPassword");
        else if (!accountJson.getPassword().matches(ServerConstants.REGEX_PASSWORD))
            throw new FPNValidationException(ServerResponseConstants.INVALID_TOO_WEAK_PASSWORD_CODE, ServerResponseConstants.INVALID_TOO_WEAK_PASSWORD_TEXT,
                "RegexFormatCheck#Password");
        else if (!accountJson.getPassword().equals(accountJson.getConfirmPassword()))
            throw new FPNValidationException(ServerResponseConstants.MISMATCH_PASSWORD_CODE, ServerResponseConstants.MISMATCH_PASSWORD_TEXT, "Mismatch#Password");
        else {
            accountService.changePassword(loggedAccount, accountJson.getPassword());

            apiResponse.setResponseCode(ServerResponseConstants.SUCCESS_CODE);
            apiResponse.setResponseText(ServerResponseConstants.SUCCESS_TEXT);

            response.setStatus(HttpServletResponse.SC_OK);

            return apiResponse;
        }
    }
}
