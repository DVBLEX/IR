USE fpn;

DROP TABLE IF EXISTS `system_checks`;

CREATE TABLE `system_checks` (
  `id` int NOT NULL,
  `name` varchar(64) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `type` int NOT NULL,
  `query_time_from` time NOT NULL,
  `query_time_to` time NOT NULL,
  `query` varchar(512) NOT NULL,
  `query_params` varchar(128) NOT NULL,
  `config_params` varchar(128) NOT NULL,
  `query_result` int NOT NULL,
  `query_execution_time` int NOT NULL,
  `comparison_operator` int NOT NULL,
  `comparison_value` int NOT NULL,
  `comparison_result` tinyint(1) NOT NULL,
  `date_edited` datetime NOT NULL,
  `date_created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `query_time_from_ik` (`query_time_from`),
  KEY `query_time_to_ik` (`query_time_to`),
  KEY `query_result_ik` (`query_result`),
  KEY `date_edited_ik` (`date_edited`),
  KEY `date_created_ik` (`date_created`),
  KEY `type_ik` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `fpn`.`system_timer_tasks` (`id`, `name`, `date_last_run`, `type`, `period`, `application`) VALUES ('101', 'smsTimerTask', '2021-10-21 10:59:45', 'continuous', '1000', 'fpn');
INSERT INTO `fpn`.`system_timer_tasks` (`id`, `name`, `date_last_run`, `type`, `period`, `application`) VALUES ('102', 'emailTimerTask', '2021-10-21 10:59:45', 'continuous', '1000', 'fpn');
INSERT INTO `fpn`.`system_timer_tasks` (`id`, `name`, `date_last_run`, `type`, `period`, `application`) VALUES ('103', 'dailyTaskExecutor', '2021-10-05 08:31:06', 'cron', '0 10 0 * * *', 'fpn');
INSERT INTO `fpn`.`system_timer_tasks` (`id`, `name`, `date_last_run`, `type`, `period`, `application`) VALUES ('104', 'systemStatusCheckTimerTask', '2021-10-21 11:03:04', 'continuous', '360000', 'fpn');

INSERT INTO `fpn`.`system_checks` (`id`, `name`, `is_active`, `type`, `query_time_from`, `query_time_to`, `query`, `query_params`, `config_params`, `query_result`, `query_execution_time`, `comparison_operator`, `comparison_value`, `comparison_result`, `date_edited`, `date_created`) VALUES ('1', 'SMS_QUEUE_CHECK', '1', '1', '00:00:00', '23:59:59', 'SELECT COUNT(1) FROM sms_scheduler WHERE is_processed != -100 AND date_scheduled < SUBTIME(now(), \'00:06:00\')', '', '', '69', '0', '2', '0', '1', '2021-11-02 15:00:00', '2021-11-02 15:00:00');
INSERT INTO `fpn`.`system_checks` (`id`, `name`, `is_active`, `type`, `query_time_from`, `query_time_to`, `query`, `query_params`, `config_params`, `query_result`, `query_execution_time`, `comparison_operator`, `comparison_value`, `comparison_result`, `date_edited`, `date_created`) VALUES ('2', 'EMAIL_QUEUE_CHECK', '1', '1', '00:00:00', '23:59:59', 'SELECT COUNT(1) FROM email_scheduler WHERE is_processed != -100 AND date_scheduled < SUBTIME(now(), \'00:12:00\')', '', '', '0', '1', '2', '0', '0', '2021-11-02 15:00:00', '2021-11-02 15:00:00');
INSERT INTO `fpn`.`system_checks` (`id`, `name`, `is_active`, `type`, `query_time_from`, `query_time_to`, `query`, `query_params`, `config_params`, `query_result`, `query_execution_time`, `comparison_operator`, `comparison_value`, `comparison_result`, `date_edited`, `date_created`) VALUES ('101', 'SMS_TIMER_TASK_CHECK', '1', '1', '00:00:00', '23:59:59', 'SELECT (CASE WHEN timestampdiff(second, date_last_run, now()) > 600 THEN 1 ELSE 0 END) AS result FROM system_timer_tasks WHERE id = 101', ' ', '', '0', '0', '2', '0', '0', '2021-11-02 15:00:00', '2021-11-02 15:00:00');
INSERT INTO `fpn`.`system_checks` (`id`, `name`, `is_active`, `type`, `query_time_from`, `query_time_to`, `query`, `query_params`, `config_params`, `query_result`, `query_execution_time`, `comparison_operator`, `comparison_value`, `comparison_result`, `date_edited`, `date_created`) VALUES ('102', 'EMAIL_TIMER_TASK_CHECK', '1', '1', '00:00:00', '23:59:59', 'SELECT (CASE WHEN timestampdiff(second, date_last_run, now()) > 600 THEN 1 ELSE 0 END) AS result FROM system_timer_tasks WHERE id = 102', ' ', '', '0', '1', '2', '0', '0', '2021-11-02 15:00:00', '2021-11-02 15:00:00');
INSERT INTO `fpn`.`system_checks` (`id`, `name`, `is_active`, `type`, `query_time_from`, `query_time_to`, `query`, `query_params`, `config_params`, `query_result`, `query_execution_time`, `comparison_operator`, `comparison_value`, `comparison_result`, `date_edited`, `date_created`) VALUES ('103', 'DAILY_TIMER_TASK_CHECK', '1', '1', '00:00:00', '23:59:59', 'SELECT (CASE WHEN timestampdiff(second, date_last_run, now()) > 90000 THEN 1 ELSE 0 END) AS result FROM system_timer_tasks WHERE id = 103', ' ', '', '1', '1', '2', '0', '1', '2021-11-02 15:00:00', '2021-11-02 15:00:00');
INSERT INTO `fpn`.`system_checks` (`id`, `name`, `is_active`, `type`, `query_time_from`, `query_time_to`, `query`, `query_params`, `config_params`, `query_result`, `query_execution_time`, `comparison_operator`, `comparison_value`, `comparison_result`, `date_edited`, `date_created`) VALUES ('104', 'SYSTEM_STATUS_TIMER_TASK_CHECK', '1', '1', '00:00:00', '23:59:59', 'SELECT (CASE WHEN timestampdiff(second, date_last_run, now()) > 600 THEN 1 ELSE 0 END) AS result FROM system_timer_tasks WHERE id = 104', ' ', '', '0', '0', '2', '0', '0', '2021-11-02 15:00:00', '2021-11-02 15:00:00');
