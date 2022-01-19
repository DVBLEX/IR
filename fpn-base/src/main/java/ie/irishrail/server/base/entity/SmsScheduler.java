package ie.irishrail.server.base.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table("sms_scheduler")
@Getter
@Setter
@ToString
public class SmsScheduler {

    @Id
    @Column("id")
    private long   id;

    @Column("is_processed")
    private int    isProcessed;

    @Column("type")
    private long   type;

    @Column("config_id")
    private long   configId;

    @Column("account_id")
    private long   accountId;

    @Column("template_id")
    private long   templateId;

    @Column("priority")
    private int    priority;

    @Column("msisdn")
    private String msisdn;

    @Column("source_addr")
    private String sourceAddr;

    @Column("message")
    private String message;

    @Column("channel")
    private int    channel;

    @Column("date_created")
    private Date   dateCreated;

    @Column("date_scheduled")
    private Date   dateScheduled;

    @Column("retry_count")
    private int    retryCount;

    @Column("date_processed")
    private Date   dateProcessed;

    @Column("transaction_id")
    private long   transactionId;

    @Column("response_code")
    private int    responseCode;

    @Column("response_text")
    private String responseText;
}
