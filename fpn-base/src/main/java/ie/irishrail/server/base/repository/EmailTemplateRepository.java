package ie.irishrail.server.base.repository;

import org.springframework.data.repository.CrudRepository;

import ie.irishrail.server.base.entity.EmailTemplate;

public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, Long> {

}
