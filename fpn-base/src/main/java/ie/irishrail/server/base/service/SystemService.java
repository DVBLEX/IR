package ie.irishrail.server.base.service;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ie.irishrail.server.base.entity.SystemParameter;
import ie.irishrail.server.base.repository.AccountRepository;
import ie.irishrail.server.base.repository.BlockedEmailDomainRepository;
import ie.irishrail.server.base.repository.EmailWhitelistRepository;
import ie.irishrail.server.base.repository.SystemParameterRepository;
import ie.irishrail.server.base.repository.SystemTimerTaskRepository;
import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class SystemService {

    private BlockedEmailDomainRepository blockedEmailDomainRepository;
    private EmailWhitelistRepository     emailWhitelistRepository;
    private SystemParameterRepository    systemParameterRepository;
    private AccountRepository            accountRepository;
    private SystemTimerTaskRepository    systemTimerTaskRepository;

    private SystemParameter              systemParameter;

    @Value("${tc.system.shortname}")
    private String                       systemShortname;

    @Value("${tc.system.environment}")
    private String                       systemEnvironment;

    @Autowired
    public void setBlockedEmailDomainRepository(BlockedEmailDomainRepository blockedEmailDomainRepository) {
        this.blockedEmailDomainRepository = blockedEmailDomainRepository;
    }

    @Autowired
    public void setEmailWhitelistRepository(EmailWhitelistRepository emailWhitelistRepository) {
        this.emailWhitelistRepository = emailWhitelistRepository;
    }

    @Autowired
    public void setSystemParameterRepository(SystemParameterRepository systemParameterRepository) {
        this.systemParameterRepository = systemParameterRepository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Autowired
    public void setSystemTimerTaskRepository(SystemTimerTaskRepository systemTimerTaskRepository) {
        this.systemTimerTaskRepository = systemTimerTaskRepository;
    }

    @PostConstruct
    public void init() {

        log.info(systemShortname + "###SYSTEM_ENVIRONMENT=" + systemEnvironment);

        systemParameter = systemParameterRepository.findById(1l).orElse(null);

        log.info("init###" + systemParameter.toString());
    }

    public SystemParameter getSystemParameter() {
        return systemParameter;
    }

    public boolean isEmailBlockedDomain(String email) throws Exception {
        if (isEmailWhitelisted(email))
            return false;

        return blockedEmailDomainRepository.countEmailBlockedDomain(email) > 0L;
    }

    public boolean isEmailWhitelisted(String email) {

        return emailWhitelistRepository.countWhitelistByEmail(email) > 0L;
    }

    public void whitelistEmail(String email) throws Exception {

        emailWhitelistRepository.whitelistEmail(email);
    }

    public boolean isCountEmailForgotPasswordUnderLimit(String email, int passwordForgotEmailLimit) throws Exception {
        return accountRepository.countEmailForgotPasswordUnderLimit(email, passwordForgotEmailLimit) == 0L;
    }

    public boolean isEmailRegisteredAlready(String email) throws Exception {
        return accountRepository.countEmailRegisteredAlready(email) > 0L;
    }

    public boolean isMsisdnRegisteredAlready(String msisdn) {
        return accountRepository.countMsisdnRegisteredAlready(msisdn) > 0L;
    }

    public void updateSystemTimerTaskDateLastRun(long timerTaskId, Date dateLastRun) {

        systemTimerTaskRepository.updateDateLastRun(dateLastRun, timerTaskId);
    }

}
