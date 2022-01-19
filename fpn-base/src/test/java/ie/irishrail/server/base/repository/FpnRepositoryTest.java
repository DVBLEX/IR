package ie.irishrail.server.base.repository;

import static ie.irishrail.server.base.common.TestData.EXISTING_FPN_NUMBER;
import static ie.irishrail.server.base.common.TestData.EXISTING_RN_NUMBER;
import static ie.irishrail.server.base.common.TestData.NON_EXISTING_FPN_NUMBER;
import static ie.irishrail.server.base.common.TestData.NON_EXISTING_RN_NUMBER;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import ie.irishrail.server.base.config.TestConfig;
import ie.irishrail.server.base.entity.FixedPenaltyNotice;

@ExtendWith(SpringExtension.class)
@DataJdbcTest
@ActiveProfiles("test")
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
class FpnRepositoryTest {

    private FpnRepository fpnRepository;

    @Autowired
    public void setFpnRepository(FpnRepository fpnRepository) {
        this.fpnRepository = fpnRepository;
    }

    @Test
    void whenFindByExistingFpnAndRn_thenReturnEntity() {
        FixedPenaltyNotice fixedPenaltyNotice = fpnRepository.findFixedPenaltyNoticeByFpnNumberAndRnNumber(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER);
        assertNotNull(fixedPenaltyNotice, "FPN should not be null");
    }

    @Test
    void whenFindByNonExistingFpnAndRn_thenReturnNull() {
        FixedPenaltyNotice fixedPenaltyNotice = fpnRepository.findFixedPenaltyNoticeByFpnNumberAndRnNumber(NON_EXISTING_FPN_NUMBER, NON_EXISTING_RN_NUMBER);
        assertNull(fixedPenaltyNotice, "FPN should be null");
    }
}
