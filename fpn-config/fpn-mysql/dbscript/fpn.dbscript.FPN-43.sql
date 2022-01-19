USE fpn;

DROP TABLE IF EXISTS `offences`;
CREATE TABLE `offences`
(
    `id`                 int            NOT NULL AUTO_INCREMENT,
    `title`              varchar(64)    NOT NULL,
    `description`        varchar(128)   NOT NULL,
    `appeal_letter_text` varchar(256)   NOT NULL,
    `amount`             decimal(10, 2) NOT NULL,
    `date_created`       datetime       NOT NULL,
    PRIMARY KEY (`id`)
);
