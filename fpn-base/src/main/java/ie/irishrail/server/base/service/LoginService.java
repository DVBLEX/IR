package ie.irishrail.server.base.service;

import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.exception.FPNValidationException;
import ie.irishrail.server.base.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.entity.Account;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class LoginService {

    private AccountRepository accountRepository;
    private SystemService     systemService;

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public void validateLoginEmailRequest(String email) throws FPNValidationException, FPNException, Exception {

        if (!email.matches(ServerConstants.REGEXP_EMAIL) || !systemService.isEmailRegisteredAlready(email)) {

            throw new FPNValidationException(ServerResponseConstants.INVALID_EMAIL_CODE, ServerResponseConstants.INVALID_EMAIL_TEXT, "RegexFormatCheck#Email");

        } else if (!systemService.isCountEmailForgotPasswordUnderLimit(email, systemService.getSystemParameter().getPasswordForgotEmailLimit())) {

            throw new FPNValidationException(ServerResponseConstants.LIMIT_EXCEEDED_EMAIL_FORGOT_PASSWORD_CODE, ServerResponseConstants.LIMIT_EXCEEDED_EMAIL_FORGOT_PASSWORD_TEXT,
                "LimitExceeded#ForgotPassword");
        }

    }

    public void passwordEmailCheck(String email) throws FPNValidationException, FPNException, Exception {
        if (!email.matches(ServerConstants.REGEXP_EMAIL)) {

            // The email invalid. It might be a hacking attempt.
            throw new FPNValidationException(ServerResponseConstants.INVALID_EMAIL_CODE, ServerResponseConstants.INVALID_EMAIL_TEXT, "RegexFormatCheck#Email");

        }

    }

    public void passwordCheck(String newPassword, String confirmPassword) throws FPNValidationException, FPNException, Exception {
        if (!newPassword.matches(ServerConstants.REGEX_PASSWORD)) {

            throw new FPNValidationException(ServerResponseConstants.INVALID_TOO_WEAK_PASSWORD_CODE, ServerResponseConstants.INVALID_TOO_WEAK_PASSWORD_TEXT,
                "RegexFormatCheck#Password");

        }
        if (!newPassword.equals(confirmPassword)) {

            throw new FPNException(ServerResponseConstants.MISMATCH_PASSWORD_CODE, ServerResponseConstants.MISMATCH_PASSWORD_TEXT, "");

        }
    }

    public void accountSave(Account account) {
        accountRepository.save(account);

    }

}
