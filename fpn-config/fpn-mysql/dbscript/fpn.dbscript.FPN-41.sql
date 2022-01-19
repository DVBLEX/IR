USE fpn;

DROP TABLE IF EXISTS `fixed_penalty_notices`;
CREATE TABLE `fixed_penalty_notices`
(
    `id`                  int            NOT NULL AUTO_INCREMENT,
    `fpn_number`          int            NOT NULL,
    `rn_number`           int            NOT NULL,
    `first_name`          varchar(64)    NOT NULL,
    `surname`             varchar(64)    NOT NULL,
    `flat`                int            NULL,
    `house_name`          varchar(64)    NULL,
    `house_number`        int            NULL,
    `street`              varchar(64)    NOT NULL,
    `locality`            varchar(64)    NOT NULL,
    `province`            varchar(64)    NOT NULL,
    `offence_id`          int            NOT NULL,
    `charge_amount`       decimal(10, 2) NOT NULL,
    `is_appeal_submitted` boolean        NOT NULL,
    `date_created`        datetime       NOT NULL,
    PRIMARY KEY (`id`)
);
