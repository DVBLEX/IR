package ie.irishrail.server.base.task;

import java.util.Date;

import ie.irishrail.server.base.common.ServerConstants;
import ie.irishrail.server.base.entity.Sms;
import ie.irishrail.server.base.exception.FPNException;
import ie.irishrail.server.base.service.SMSService;

// @CommonsLog
public class SmsTaskExecutor implements Runnable {

    private SMSService smsService;

    private Sms        sms;

    public SmsTaskExecutor(Sms sms, SMSService smsService) {
        this.sms = sms;
        this.smsService = smsService;
    }

    @Override
    public void run() {

        try {
            // log.info("run#smsId=" + sms.getId());
            smsService.sendBulkSms(sms);

            sms.setIsProcessed(ServerConstants.PROCESS_PROCESSED);
            smsService.updateSms(sms);
            smsService.deleteScheduledSms(sms.getId());

        } catch (FPNException fpne) {

            sms.setResponseCode(fpne.getResponseCode());
            sms.setResponseText(fpne.getResponseText());

            if (fpne.getResponseCode() == 3105 || fpne.getResponseCode() == 3106) {

                sms.setIsProcessed(ServerConstants.PROCESS_PROCESSED);
                smsService.updateSms(sms);
                smsService.deleteScheduledSms(sms.getId());

            } else {
                sms.setRetryCount(sms.getRetryCount() + 1);
                if (sms.getRetryCount() == 1) {
                    sms.setIsProcessed(ServerConstants.PROCESS_NOTPROCESSED);
                    sms.setDateScheduled(new Date(sms.getDateScheduled().getTime() + 5l * 1000l));
                    smsService.updateScheduledSms(sms);

                } else if (sms.getRetryCount() < 4) {
                    sms.setIsProcessed(ServerConstants.PROCESS_NOTPROCESSED);
                    sms.setDateScheduled(new Date(sms.getDateScheduled().getTime() + 30l * 1000l));
                    smsService.updateScheduledSms(sms);

                } else {
                    smsService.updateScheduledSms(sms);
                    // smsService.updateSms(sms);
                    // smsService.deleteScheduledSms(sms.getId());
                }
            }
        }
    }
}
