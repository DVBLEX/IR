package ie.irishrail.server.base.entity;

import java.util.Date;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table("email_code_requests")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailCodeRequest {

    private long   id;
    private String email;
    private String code;
    private String token;
    private int    countCodeSent;
    private int    countVerified;
    private Date   dateCodeSent;
    private Date   dateVerified;
    private Date   dateCreated;
}
