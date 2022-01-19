package ie.irishrail.server.base.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table("account_activity_log")
@Getter
@Setter
@ToString
public class AccountActivityLog {

    @Id
    @Column("id")
    private Long   id;

    @Column("account_id")
    private Long   accountId;

    @Column("activity_id")
    private Long   activityId;

    @Column("activity_name")
    private String activityName;

    @Column("json")
    private String json;

    @Column("date_created")
    private Date   dateCreated;
}
