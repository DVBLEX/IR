package ie.irishrail.server.base.task;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.service.EmailService;
import ie.irishrail.server.base.service.SystemService;
import lombok.extern.apachecommons.CommonsLog;

@Component
@CommonsLog
public class DailyTimerTask {

    private EmailService  emailService;
    private SystemService systemService;

    @Autowired
    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }

    @Scheduled(cron = "0 10 0 * * *")
    public void run() {

        try {
            log.info("run###");

            systemService.updateSystemTimerTaskDateLastRun(ServerConstants.SYSTEM_TIMER_TASK_DAILY_ID, new Date());

        } catch (DataAccessException dae) {

            log.error("DailyTimerTask##DataAccessException: ", dae);

            emailService.sendSystemEmail("DailyTimerTask DataAccessException", EmailService.EMAIL_TYPE_EXCEPTION, null, null, "DailyTimerTask#run###DataAccessException:<br />"
                + dae.getMessage());

        } catch (Exception e) {

            log.error("DailyTimerTask##Exception: ", e);

            emailService.sendSystemEmail("DailyTimerTask Exception", EmailService.EMAIL_TYPE_EXCEPTION, null, null, "DailyTimerTask#run###Exception:<br />" + e.getMessage());
        }
    }
}
