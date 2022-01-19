package ie.irishrail.server.rest.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.irishrail.server.base.common.SecurityUtil;
import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.entity.Account;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.exception.FPNValidationException;
import ie.irishrail.server.base.jsonentity.ApiResponseJsonEntity;
import ie.irishrail.server.base.jsonentity.LoginJson;
import ie.irishrail.server.base.service.AccountService;
import ie.irishrail.server.base.service.CaptchaService;
import ie.irishrail.server.base.service.LoginService;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@RestController
@RequestMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private PasswordEncoder passwordEncoder;
    private AccountService  accountService;
    private CaptchaService  captchaService;
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
    public void setCaptchaService(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/password/forgot/send")
    public ApiResponseJsonEntity forgotPasswordSend(HttpServletResponse response, @RequestBody LoginJson login) throws FPNException, FPNValidationException, Exception {

        captchaService.processResponse(login.getRecaptchaResponse());

        ApiResponseJsonEntity apiResponse = new ApiResponseJsonEntity();

        loginService.validateLoginEmailRequest(login.getEmail());

        accountService.sendPasswdForgotEmail(login.getEmail());

        apiResponse.setResponseCode(ServerResponseConstants.SUCCESS_CODE);
        apiResponse.setResponseText(ServerResponseConstants.SUCCESS_TEXT);
        response.setStatus(HttpServletResponse.SC_OK);

        return apiResponse;
    }

    @PutMapping("/password/forgot/change")
    public ApiResponseJsonEntity forgotPasswordChange(HttpServletResponse response, @RequestBody LoginJson login) throws FPNException, FPNValidationException, Exception {

        captchaService.processResponse(login.getRecaptchaResponse());

        ApiResponseJsonEntity apiResponse = new ApiResponseJsonEntity();

        loginService.passwordEmailCheck(login.getEmail());

        loginService.passwordCheck(login.getNewPassword(), login.getConfirmPassword());

        if (!login.getKey().matches(ServerConstants.REGEX_SHA256) || !login.getSecondKey().matches(ServerConstants.REGEX_SHA256)) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new FPNException(ServerResponseConstants.API_FAILURE_CODE, ServerResponseConstants.API_FAILURE_TEXT, "RegexFormatCheck#Token");

        } else {
            Account account = accountService.getActiveAccountByUsername(login.getEmail());

            String token1 = SecurityUtil.generateDateBasedToken1(login.getEmail(), account.getDateLastPasswdForgotRequest());
            String token2 = SecurityUtil.generateDateBasedToken2(login.getEmail(), account.getDateLastPasswdForgotRequest());

            if (!token1.equals(login.getKey()) || !token2.equals(login.getSecondKey())) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                throw new FPNException(ServerResponseConstants.API_FAILURE_CODE, ServerResponseConstants.API_FAILURE_TEXT, "Conflict#TokenValidation");

            } else {
                account.setPassword(passwordEncoder.encode(login.getNewPassword()));
                account.setIsCredentialsExpired(false);
                account.setDateLastPassword(new Date());
                account.setLoginFailureCount(0);
                account.setIsLocked(false);
                account.setCountPasswdForgotRequests(0);

                loginService.accountSave(account);

                response.setStatus(HttpServletResponse.SC_OK);

                apiResponse.setResponseCode(ServerResponseConstants.SUCCESS_CODE);
                apiResponse.setResponseText(ServerResponseConstants.SUCCESS_TEXT);
            }
        }
        return apiResponse;
    }

    @PutMapping("/password/expired/update")
    public ApiResponseJsonEntity expiredPasswordUpdate(HttpServletResponse response, @RequestBody LoginJson login) throws FPNException, Exception {

        captchaService.processResponse(login.getRecaptchaResponse());

        ApiResponseJsonEntity apiResponse = new ApiResponseJsonEntity();

        loginService.passwordEmailCheck(login.getEmail());

        loginService.passwordCheck(login.getNewPassword(), login.getConfirmPassword());

        if (!login.getKey().matches(ServerConstants.REGEX_SHA256) || !login.getSecondKey().matches(ServerConstants.REGEX_SHA256)) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new FPNException(ServerResponseConstants.API_FAILURE_CODE, ServerResponseConstants.API_FAILURE_TEXT, "RegexFormatCheck#Token");

        } else {
            Account account = accountService.getActiveAccountByUsername(login.getEmail());

            String token1 = SecurityUtil.generateDateBasedToken1(login.getEmail(), account.getDateLastPassword());
            String token2 = SecurityUtil.generateDateBasedToken2(login.getEmail(), account.getDateLastPassword());

            if (!token1.equals(login.getKey()) || !token2.equals(login.getSecondKey())) {

                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                throw new FPNException(ServerResponseConstants.API_FAILURE_CODE, ServerResponseConstants.API_FAILURE_TEXT, "Conflict#TokenValidation");

            } else {
                if (!account.getIsCredentialsExpired())
                    // The credentials are not expired. It might be a hacking attempt.
                    throw new FPNException(ServerResponseConstants.API_FAILURE_CODE, ServerResponseConstants.API_FAILURE_TEXT, "Conflict#CredentialsAreNotExpired");

                if (!passwordEncoder.matches(login.getOldPassword(), account.getPassword()))
                    throw new FPNValidationException(ServerResponseConstants.INVALID_OLD_PASSWORD_CODE, ServerResponseConstants.INVALID_OLD_PASSWORD_TEXT, "InvalidOldPassword");

                else if (passwordEncoder.matches(login.getNewPassword(), account.getPassword()))
                    throw new FPNValidationException(ServerResponseConstants.INVALID_NEW_PASSWORD_CODE, ServerResponseConstants.INVALID_NEW_PASSWORD_TEXT, "InvalidNewPassword");

                else {
                    account.setPassword(passwordEncoder.encode(login.getNewPassword()));
                    account.setIsCredentialsExpired(false);
                    account.setDateLastPassword(new Date());
                    account.setLoginFailureCount(0);
                    account.setIsLocked(false);
                    account.setCountPasswdForgotRequests(0);

                    loginService.accountSave(account);

                    response.setStatus(HttpServletResponse.SC_OK);

                    apiResponse.setResponseCode(ServerResponseConstants.SUCCESS_CODE);
                    apiResponse.setResponseText(ServerResponseConstants.SUCCESS_TEXT);
                }
            }
        }

        return apiResponse;
    }
}
