package ie.irishrail.server.base.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ie.irishrail.server.base.entity.SystemParameter;

@Repository
public interface SystemParameterRepository extends CrudRepository<SystemParameter, Long> {

}
