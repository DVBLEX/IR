package ie.irishrail.server.base.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ie.irishrail.server.base.entity.Offence;

@Repository
public interface OffenceRepository extends CrudRepository<Offence, Long> {

}
