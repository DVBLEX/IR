package ie.irishrail.server.base.repository;

import org.springframework.data.repository.CrudRepository;

import ie.irishrail.server.base.entity.EmailConfig;

public interface EmailConfigRepository extends CrudRepository<EmailConfig, Long> {

}
