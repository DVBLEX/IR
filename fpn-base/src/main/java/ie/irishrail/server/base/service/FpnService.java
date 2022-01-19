package ie.irishrail.server.base.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.entity.FixedPenaltyNotice;
import ie.irishrail.server.base.entity.Offence;
import ie.irishrail.server.base.exception.FPNNotFoundException;
import ie.irishrail.server.base.repository.FpnRepository;
import ie.irishrail.server.base.repository.OffenceRepository;

@Service
public class FpnService {

    private FpnRepository     fpnRepository;
    private OffenceRepository offenceRepository;

    @Autowired
    public void setFpnRepository(FpnRepository fpnRepository) {
        this.fpnRepository = fpnRepository;
    }

    @Autowired
    public void setOffenceRepository(OffenceRepository offenceRepository) {
        this.offenceRepository = offenceRepository;
    }

    public FixedPenaltyNotice search(int fpnNumber, int rnNumber) {
        FixedPenaltyNotice fixedPenaltyNotice = fpnRepository.findFixedPenaltyNoticeByFpnNumberAndRnNumber(fpnNumber, rnNumber);
        if (fixedPenaltyNotice == null) {
            throw new FPNNotFoundException(ServerResponseConstants.API_FAILURE_CODE, "Fixed Penalty Notice has not been found", "FpnService#search");
        }
        Optional<Offence> offenceOptional = offenceRepository.findById(fixedPenaltyNotice.getOffenceId());
        offenceOptional.ifPresent(fixedPenaltyNotice::setOffence);
        return fixedPenaltyNotice;
    }

}
