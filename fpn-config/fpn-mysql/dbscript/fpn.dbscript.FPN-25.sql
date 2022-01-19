USE fpn;

CREATE TABLE `system_parameters`
(
    `id`                                 int          NOT NULL,
    `errors_from_email`                  varchar(255) NOT NULL,
    `errors_from_email_password`         varchar(255) NOT NULL,
    `errors_to_email`                    varchar(255) NOT NULL,
    `contact_email`                      varchar(64)  NOT NULL,
    `password_forgot_email_limit`        int          NOT NULL,
    `reg_email_code_send_limit`          int          NOT NULL,
    `reg_email_verification_limit`       int          NOT NULL,
    `reg_email_code_valid_minutes`       int          NOT NULL,
    `reg_email_verification_valid_hours` int          NOT NULL,
    `reg_sms_code_send_limit`            int          NOT NULL,
    `reg_sms_verification_limit`         int          NOT NULL,
    `reg_sms_code_valid_minutes`         int          NOT NULL,
    `reg_sms_verification_valid_hours`   int          NOT NULL,
    `reg_link_valid_hours`               int          NOT NULL,
    `login_lock_count_failed`            int          NOT NULL,
    `login_lock_period`                  int          NOT NULL,
    `login_password_valid_period`        int          NOT NULL,
    `password_forgot_url_valid_minutes`  int          NOT NULL,
    `files_expiry_days`                  int          NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

INSERT INTO `system_parameters` (`id`, `errors_from_email`, `errors_from_email_password`, `errors_to_email`, `contact_email`, `password_forgot_email_limit`,
                                 `reg_email_code_send_limit`,
                                 `reg_email_verification_limit`, `reg_email_code_valid_minutes`, `reg_email_verification_valid_hours`, `reg_sms_code_send_limit`,
                                 `reg_sms_verification_limit`,
                                 `reg_sms_code_valid_minutes`, `reg_sms_verification_valid_hours`, `reg_link_valid_hours`, `login_lock_count_failed`, `login_lock_period`,
                                 `login_password_valid_period`, `password_forgot_url_valid_minutes`, `files_expiry_days`)
VALUES (1, 'errors.httpinterface@telclic.net', 'telclic p@55w0rd', 'errors@telclic.net', 'errors@telclic.net', 3, 10, 3, 30, 4, 10, 3, 30, 4, 24, 5, 6, 182, 120, 10);

CREATE TABLE `email_config`
(
    `id`                   int         NOT NULL,
    `smtp_host`            varchar(64) NOT NULL,
    `smtp_auth`            varchar(8)  NOT NULL,
    `smtp_port`            varchar(8)  NOT NULL,
    `smtp_starttls_enable` varchar(8)   DEFAULT NULL,
    `smtp_ssl_protocols`   varchar(128) DEFAULT NULL,
    `account_id`           int         NOT NULL,
    `date_created`         datetime    NOT NULL,
    `date_edited`          datetime    NOT NULL,
    PRIMARY KEY (`id`),
    KEY `account_id_ik` (`account_id`),
    KEY `date_created_ik` (`date_created`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `email_scheduler`
(
    `id`              int           NOT NULL,
    `is_processed`    int           NOT NULL,
    `type`            int           NOT NULL,
    `config_id`       int           NOT NULL,
    `account_id`      int           NOT NULL,
    `template_id`     int           NOT NULL,
    `priority`        int           NOT NULL,
    `email_to`        varchar(256)  NOT NULL,
    `email_reply_to`  varchar(256) DEFAULT NULL,
    `email_bcc`       varchar(256) DEFAULT NULL,
    `subject`         varchar(128)  NOT NULL,
    `message`         varchar(8192) NOT NULL,
    `channel`         int           NOT NULL,
    `attachment_path` varchar(256)  NOT NULL,
    `date_created`    datetime      NOT NULL,
    `date_scheduled`  datetime      NOT NULL,
    `retry_count`     int           NOT NULL,
    `date_processed`  datetime     DEFAULT NULL,
    `response_code`   varchar(128)  NOT NULL,
    `response_text`   varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `date_scheduled_ik` (`date_scheduled`),
    KEY `is_processed_ik` (`is_processed`),
    KEY `priority_ik` (`priority`),
    KEY `template_id_ik` (`template_id`),
    KEY `email_to_ik` (`email_to`(255)),
    KEY `account_id_ik` (`account_id`),
    KEY `retry_count_ik` (`retry_count`),
    KEY `date_processed_ik` (`date_processed`),
    KEY `config_id_ik` (`config_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `email_templates`
(
    `id`                  int           NOT NULL,
    `type`                int           NOT NULL,
    `name`                varchar(64)   NOT NULL,
    `config_id`           int           NOT NULL,
    `email_from`          varchar(64)  DEFAULT NULL,
    `email_from_password` varchar(64)  DEFAULT NULL,
    `email_bcc`           varchar(256) DEFAULT NULL,
    `subject`             varchar(128)  NOT NULL,
    `template`            varchar(8192) NOT NULL,
    `message`             varchar(8192) NOT NULL,
    `variables`           varchar(128)  NOT NULL,
    `priority`            int           NOT NULL,
    `account_id`          int           NOT NULL,
    `date_created`        datetime      NOT NULL,
    `date_edited`         datetime      NOT NULL,
    PRIMARY KEY (`id`),
    KEY `config_id_ik` (`config_id`),
    KEY `date_created_ik` (`date_created`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `sms_config`
(
    `id`           int          NOT NULL,
    `url`          varchar(128) NOT NULL,
    `username`     varchar(32)  NOT NULL,
    `password`     varchar(32)  NOT NULL,
    `account_id`   int          NOT NULL,
    `date_created` datetime     NOT NULL,
    `date_edited`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `account_id_ik` (`account_id`),
    KEY `date_created_ik` (`date_created`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `sms_scheduler`
(
    `id`             int          NOT NULL AUTO_INCREMENT,
    `is_processed`   int          NOT NULL,
    `type`           int          DEFAULT NULL,
    `config_id`      varchar(11)  NOT NULL,
    `account_id`     int          NOT NULL,
    `template_id`    int          NOT NULL,
    `priority`       int          NOT NULL,
    `msisdn`         varchar(32)  NOT NULL,
    `source_addr`    varchar(32)  NOT NULL,
    `message`        varchar(320) NOT NULL,
    `channel`        int          NOT NULL,
    `date_created`   datetime     NOT NULL,
    `date_scheduled` datetime     NOT NULL,
    `retry_count`    int          NOT NULL,
    `date_processed` datetime     DEFAULT NULL,
    `transaction_id` int          NOT NULL,
    `response_code`  int          NOT NULL,
    `response_text`  varchar(256) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `date_scheduled_ik` (`date_scheduled`),
    KEY `msisdn_ik` (`msisdn`),
    KEY `template_id_ik` (`template_id`),
    KEY `priority_ik` (`priority`),
    KEY `is_processed_ik` (`is_processed`),
    KEY `channel_ik` (`channel`),
    KEY `date_created_ik` (`date_created`),
    KEY `date_processed_ik` (`date_processed`),
    KEY `transaction_id_ik` (`transaction_id`),
    KEY `response_code_ik` (`response_code`),
    KEY `account_id_ik` (`account_id`),
    KEY `type_ik` (`type`),
    KEY `config_id_ik` (`config_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `sms_templates`
(
    `id`           int          NOT NULL,
    `type`         int          NOT NULL,
    `name`         varchar(64)  NOT NULL,
    `config_id`    int          NOT NULL,
    `source_addr`  varchar(32)  NOT NULL,
    `message`      varchar(640) NOT NULL,
    `variables`    varchar(128) NOT NULL,
    `priority`     int          NOT NULL,
    `account_id`   int          NOT NULL,
    `date_created` datetime     NOT NULL,
    `date_edited`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    KEY `type_ik` (`type`),
    KEY `date_created_ik` (`date_created`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE `system_timer_tasks`
(
    `id`            int         NOT NULL,
    `name`          varchar(64) NOT NULL,
    `date_last_run` datetime    NOT NULL,
    `type`          varchar(16) NOT NULL,
    `period`        varchar(32) NOT NULL,
    `application`   varchar(32) NOT NULL,
    PRIMARY KEY (`id`),
    KEY `date_last_run_ik` (`date_last_run`),
    KEY `type_ik` (`type`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
