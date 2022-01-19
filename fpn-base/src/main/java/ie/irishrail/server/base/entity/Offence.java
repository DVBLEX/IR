package ie.irishrail.server.base.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table("offences")
@Getter
@Setter
@ToString
public class Offence {

    @Id
    @Column("id")
    private Long       id;

    @Column("title")
    private String     title;

    @Column("description")
    private String     description;

    @Column("appeal_letter_text")
    private String     appealLetterText;

    @Column("amount")
    private BigDecimal amount;
}
