package ie.irishrail.server.base.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table("sms_config")
@Getter
@Setter
@ToString
public class SmsConfig {

    @Id
    @Column("id")
    private long   id;

    @Column("url")
    private String url;

    @Column("username")
    private String username;

    @Column("password")
    @ToString.Exclude
    private String password;

    @Column("account_id")
    private long   accountId;

    @Column("date_created")
    private Date   dateCreated;

    @Column("date_edited")
    private Date   dateEdited;
}
