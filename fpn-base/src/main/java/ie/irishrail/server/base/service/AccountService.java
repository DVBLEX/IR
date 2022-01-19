package ie.irishrail.server.base.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ie.irishrail.server.base.common.SecurityUtil;
import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.entity.Account;
import ie.irishrail.server.base.entity.AccountActivityLog;
import ie.irishrail.server.base.entity.Email;
import ie.irishrail.server.base.entity.NameValuePair;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.repository.AccountActivityLogRepository;
import ie.irishrail.server.base.repository.AccountRepository;

@Service
public class AccountService {

    private final ConcurrentMap<Long, NameValuePair> activityLogMap = new ConcurrentHashMap<>();

    private PasswordEncoder                          passwordEncoder;
    private AccountActivityLogRepository             accountActivityLogRepository;
    private AccountRepository                        accountRepository;
    private EmailService                             emailService;
    private SystemService                            systemService;

    @Value("${tc.system.url}")
    private String                                   systemUrl;
    @Value("${tc.passwordForgotChange.url}")
    private String                                   passwordForgotChangeUrl;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setAccountActivityLogRepository(AccountActivityLogRepository accountActivityLogRepository) {
        this.accountActivityLogRepository = accountActivityLogRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    public String getActivityNameById(long activityId) {

        return activityLogMap.get(activityId).getName();
    }

    @Transactional
    public void sendPasswdForgotEmail(String emailTo) throws FPNException, Exception {

        Account account = accountRepository.findByUsername(emailTo);

        account.setCountPasswdForgotRequests(account.getCountPasswdForgotRequests() + 1);

        Date dateForgotPasswdRequest = new Date();

        String token1 = SecurityUtil.generateDateBasedToken1(emailTo, dateForgotPasswdRequest);
        String token2 = SecurityUtil.generateDateBasedToken2(emailTo, dateForgotPasswdRequest);

        Email email = new Email();
        email.setEmailTo(emailTo);

        HashMap<String, Object> params = new HashMap<>();
        params.put("firstName", account.getFirstName());
        params.put("resetPasswordLink", systemUrl + passwordForgotChangeUrl + URLEncoder.encode(emailTo, StandardCharsets.UTF_8.name()) + "&t=" + token1 + "&t2=" + token2);
        params.put("validMinutes", systemService.getSystemParameter().getPasswordForgotUrlValidMinutes());

        emailService.scheduleEmailByType(email, ServerConstants.EMAIL_PASSWORD_FORGOT_TEMPLATE_ID, params);

        account.setDateLastPasswdForgotRequest(dateForgotPasswdRequest);

        accountRepository.save(account);
    }

    public Account getActiveAccountByUsername(String username) {

        Account account = accountRepository.findByUsername(username);

        if (account == null || account.getIsDeleted())
            return null;

        return account;
    }

    @Transactional
    public void logAccountActivity(long accountId, long activityId, String json) throws Exception {

        String activityName = getActivityNameById(activityId);

        AccountActivityLog accountActivityLog = new AccountActivityLog();
        accountActivityLog.setAccountId(accountId);
        accountActivityLog.setActivityId(activityId);
        accountActivityLog.setActivityName(activityName);
        accountActivityLog.setJson(json);
        accountActivityLog.setDateCreated(new Date());

        accountActivityLogRepository.save(accountActivityLog);
    }

    @Transactional
    public void changePassword(Account loggedAccount, String newPassword) {

        loggedAccount.setPassword(passwordEncoder.encode(newPassword));
        loggedAccount.setIsCredentialsExpired(false);
        loggedAccount.setDateLastPassword(new Date());
        loggedAccount.setLoginFailureCount(0);
        loggedAccount.setCountPasswdForgotRequests(0);
        loggedAccount.setDateEdited(new Date());

        accountRepository.save(loggedAccount);
    }

    public Account getLoggedAccount() throws FPNException {

        Account loggedAccount = accountRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if (loggedAccount == null)
            throw new FPNException(ServerResponseConstants.API_FAILURE_CODE, ServerResponseConstants.API_FAILURE_TEXT, "loggedAccount is null");

        return loggedAccount;
    }
}
