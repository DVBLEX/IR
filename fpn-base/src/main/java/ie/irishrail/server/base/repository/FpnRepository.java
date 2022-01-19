package ie.irishrail.server.base.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import ie.irishrail.server.base.entity.FixedPenaltyNotice;

@Repository
public interface FpnRepository extends CrudRepository<FixedPenaltyNotice, Long> {

    FixedPenaltyNotice findFixedPenaltyNoticeByFpnNumberAndRnNumber(int fpnNumber, int rnNumber);
}
