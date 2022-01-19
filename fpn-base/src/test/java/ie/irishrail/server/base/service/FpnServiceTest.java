package ie.irishrail.server.base.service;

import static ie.irishrail.server.base.common.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import ie.irishrail.server.base.entity.Offence;
import ie.irishrail.server.base.repository.OffenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ie.irishrail.server.base.entity.FixedPenaltyNotice;
import ie.irishrail.server.base.exception.FPNNotFoundException;
import ie.irishrail.server.base.repository.FpnRepository;

@ExtendWith(MockitoExtension.class)
class FpnServiceTest {

    @Mock
    private FpnRepository fpnRepository;

    @Mock
    private OffenceRepository offenceRepository;

    @InjectMocks
    private FpnService    fpnService;

    @BeforeEach
    public void setUp() {
        this.fpnService = new FpnService();
        this.fpnService.setFpnRepository(fpnRepository);
        this.fpnService.setOffenceRepository(offenceRepository);
        when(this.fpnRepository.findFixedPenaltyNoticeByFpnNumberAndRnNumber(anyInt(), anyInt())).thenReturn(null);
    }

    @Test
    void whenSearchExistingFpn_thenReturnExistingFilledFPNEntity() {
        when(this.fpnRepository.findFixedPenaltyNoticeByFpnNumberAndRnNumber(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER)).thenReturn(getFpnEntityStub());
        FixedPenaltyNotice fpn = this.fpnService.search(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER);
        assertEquals(EXISTING_FPN_NUMBER, fpn.getFpnNumber(), "FPN numbers should be equal");
        assertEquals(EXISTING_RN_NUMBER, fpn.getRnNumber(), "RN numbers should be equal");
        assertNotNull(fpn.getId(), "Id should be filled");
        assertNotNull(fpn.getFirstName(), "First name should be filled");
        assertNotNull(fpn.getSurname(), "Surname should be filled");
        assertNotNull(fpn.getFlat(), "Flat should be filled");
        assertNotNull(fpn.getHouseName(), "House name should be filled");
        assertNotNull(fpn.getHouseNumber(), "House number should be filled");
        assertNotNull(fpn.getStreet(), "Street should be filled");
        assertNotNull(fpn.getLocality(), "Locality (city, town) should be filled");
        assertNotNull(fpn.getProvince(), "Province should be filled");
        assertNotNull(fpn.getOffence(), "Offence should be filled");
        assertNotNull(fpn.getChargeAmount(), "Charge amount should be filled");
        assertNotNull(fpn.getIsAppealSubmitted(), "Appeal submission status should be filled");
        assertNotNull(fpn.getDateCreated(), "Date created should be filled");
    }

    @Test
    void whenSearchNonExistingFpn_thenThrowNotFoundException() {
        assertThrows(FPNNotFoundException.class, () -> {
            FixedPenaltyNotice fpn = this.fpnService.search(NON_EXISTING_FPN_NUMBER, NON_EXISTING_RN_NUMBER);
        });
    }

    @Test
    void whenSearchFpn_thenCallFpnRepository() {
        when(this.fpnRepository.findFixedPenaltyNoticeByFpnNumberAndRnNumber(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER)).thenReturn(getFpnEntityStub());
        FixedPenaltyNotice fpn = this.fpnService.search(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER);
        verify(fpnRepository, atLeastOnce()).findFixedPenaltyNoticeByFpnNumberAndRnNumber(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER);
        assertNotNull(fpn);
    }

    @Test
    void whenSearchFpn_thenCallOffenceRepository() {
        when(this.fpnRepository.findFixedPenaltyNoticeByFpnNumberAndRnNumber(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER)).thenReturn(getFpnEntityStub());
        when(this.offenceRepository.findById(OFFENCE_ID)).thenReturn(Optional.of(getOffenceStub()));
        FixedPenaltyNotice fpn = this.fpnService.search(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER);
        verify(fpnRepository, atLeastOnce()).findFixedPenaltyNoticeByFpnNumberAndRnNumber(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER);
        assertNotNull(fpn);
        assertNotNull(fpn.getOffence());
    }

    private FixedPenaltyNotice getFpnEntityStub() {
        FixedPenaltyNotice fixedPenaltyNotice = new FixedPenaltyNotice();
        fixedPenaltyNotice.setId(1L);
        fixedPenaltyNotice.setFpnNumber(EXISTING_FPN_NUMBER);
        fixedPenaltyNotice.setRnNumber(EXISTING_RN_NUMBER);
        fixedPenaltyNotice.setFirstName("John");
        fixedPenaltyNotice.setSurname("Doe");
        fixedPenaltyNotice.setFlat(1);
        fixedPenaltyNotice.setHouseName("");
        fixedPenaltyNotice.setHouseNumber(10);
        fixedPenaltyNotice.setStreet("Inchaboy");
        fixedPenaltyNotice.setLocality("Gort");
        fixedPenaltyNotice.setProvince("County Galway");
        fixedPenaltyNotice.setOffenceId(OFFENCE_ID);
        fixedPenaltyNotice.setChargeAmount(BigDecimal.valueOf(14.5));
        fixedPenaltyNotice.setIsAppealSubmitted(true);
        fixedPenaltyNotice.setOffence(getOffenceStub());
        fixedPenaltyNotice.setDateCreated(LocalDateTime.now());
        return fixedPenaltyNotice;
    }

    private Offence getOffenceStub() {
        Offence offence = new Offence();
        offence.setTitle("Railway Safety Act 2005, Part 15, Section 129");
        offence.setDescription("Failure of refusal to give name or address...");
        offence.setAppealLetterText("You were in breach of Railway Safety Act 2005...");
        offence.setAmount(BigDecimal.valueOf(100.0));
        return offence;
    }
}
