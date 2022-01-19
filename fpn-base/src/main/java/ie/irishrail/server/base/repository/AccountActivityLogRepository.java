package ie.irishrail.server.base.repository;

import ie.irishrail.server.base.entity.AccountActivityLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountActivityLogRepository extends CrudRepository<AccountActivityLog, Long> {

}
