package ie.irishrail.server.base.repository;

import org.springframework.data.repository.CrudRepository;

import ie.irishrail.server.base.entity.SmsTemplate;

public interface SmsTemplateRepository extends CrudRepository<SmsTemplate, Long> {

}
