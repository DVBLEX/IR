package ie.irishrail.server.base.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("fixed_penalty_notices")
@Getter
@Setter
@ToString
public class FixedPenaltyNotice {

    @Id
    @Column("id")
    private Long          id;

    @Column("fpn_number")
    private Integer       fpnNumber;

    @Column("rn_number")
    private Integer       rnNumber;

    @Column("first_name")
    private String        firstName;

    @Column("surname")
    private String        surname;

    @Column("flat")
    private Integer       flat;

    @Column("house_name")
    private String        houseName;

    @Column("house_number")
    private Integer       houseNumber;

    @Column("street")
    private String        street;

    @Column("locality")
    private String        locality;

    @Column("province")
    private String        province;

    @Column("offence_id")
    private Long          offenceId;

    @Column("charge_amount")
    private BigDecimal    chargeAmount;

    @Column("is_appeal_submitted")
    private Boolean       isAppealSubmitted;

    @Column("date_created")
    private LocalDateTime dateCreated;

    @Transient
    private Offence       offence;
}
