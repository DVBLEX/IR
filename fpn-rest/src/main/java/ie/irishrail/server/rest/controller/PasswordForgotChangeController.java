package ie.irishrail.server.rest.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.entity.Account;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.exception.FPNValidationException;
import ie.irishrail.server.base.jsonentity.ApiResponseJsonEntity;
import ie.irishrail.server.base.jsonentity.ChangePasswordJson;
import ie.irishrail.server.base.service.AccountService;
import ie.irishrail.server.base.service.LoginService;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping(value = "/passwordforgotchange", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PasswordForgotChangeController {

    private PasswordEncoder passwordEncoder;
    private AccountService  accountService;
    private LoginService    loginService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/set/new/password")
    public ApiResponseJsonEntity changePassword(HttpServletResponse response, @RequestBody ChangePasswordJson changePassword) throws FPNException, Exception {

        ApiResponseJsonEntity apiResponse = new ApiResponseJsonEntity();

        loginService.passwordCheck(changePassword.getNewPassword(), changePassword.getConfirmPassword());

        Account account = accountService.getLoggedAccount();

        if (!passwordEncoder.matches(changePassword.getOldPassword(), account.getPassword()))
            throw new FPNValidationException(ServerResponseConstants.INVALID_OLD_PASSWORD_CODE, ServerResponseConstants.INVALID_OLD_PASSWORD_TEXT, "InvalidOldPassword");

        else if (passwordEncoder.matches(changePassword.getNewPassword(), account.getPassword()))
            throw new FPNValidationException(ServerResponseConstants.INVALID_NEW_PASSWORD_CODE, ServerResponseConstants.INVALID_NEW_PASSWORD_TEXT, "InvalidNewPassword");

        else {
            account.setPassword(passwordEncoder.encode(changePassword.getNewPassword()));
            account.setDateLastPassword(new Date());
            account.setIsLocked(false);

            loginService.accountSave(account);

            response.setStatus(HttpServletResponse.SC_OK);

            apiResponse.setResponseCode(ServerResponseConstants.SUCCESS_CODE);
            apiResponse.setResponseText(ServerResponseConstants.SUCCESS_TEXT);
        }
        return apiResponse;
    }
}
