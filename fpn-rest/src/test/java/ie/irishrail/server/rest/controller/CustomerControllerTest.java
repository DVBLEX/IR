package ie.irishrail.server.rest.controller;

import static ie.irishrail.server.rest.config.TestData.*;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import ie.irishrail.server.base.common.ServerResponseConstants;
import ie.irishrail.server.base.exception.FPNNotFoundException;
import ie.irishrail.server.rest.aspect.CustomRestControllerAdvice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ie.irishrail.server.base.entity.FixedPenaltyNotice;
import ie.irishrail.server.base.entity.Offence;
import ie.irishrail.server.base.repository.AccountRepository;
import ie.irishrail.server.base.service.FpnService;
import ie.irishrail.server.base.service.SystemService;
import ie.irishrail.server.rest.security.MyUserDetailsService;

@ActiveProfiles("test")
@Import(CustomRestControllerAdvice.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = { CustomerController.class }, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc               mockMvc;
    @MockBean
    private AccountRepository     accountRepository;
    @MockBean
    private FpnService            fpnService;
    @MockBean
    private SystemService         systemService;
    @MockBean
    private MyUserDetailsService  myUserDetailsService;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        //This
        Arrays.stream(webApplicationContext.getBeanDefinitionNames()).map(name -> webApplicationContext.getBean(name).getClass().getName()).sorted().forEach(System.out::println);
    }

    @Test
    void greetingShouldReturnMessageFromService() throws Exception {
        when(fpnService.search(EXISTING_FPN_NUMBER, EXISTING_RN_NUMBER)).thenReturn(getFpnEntityStub());
        this.mockMvc.perform(post("/customer/fpn-search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"fpnNumber\": \"50001\", \"rnNumber\": \"14020\"}"))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.fpnNumber").value(String.valueOf(EXISTING_FPN_NUMBER)))
            .andExpect(jsonPath("$.rnNumber").value(String.valueOf(EXISTING_RN_NUMBER)));
    }

    @Test
    void whenSearchNonExistingFpn_thenGetErrorResponse() throws Exception {
        when(fpnService.search(NON_EXISTING_FPN_NUMBER, NON_EXISTING_RN_NUMBER)).thenThrow(FPNNotFoundException.class);
        this.mockMvc.perform(post("/customer/fpn-search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{\"fpnNumber\": \"0\", \"rnNumber\": \"0\"}"))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().contentType("application/json"));
    }

    @Test
    void whenSearchWithEmptyJson_thenGetErrorResponse() throws Exception {
        this.mockMvc.perform(post("/customer/fpn-search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("{}"))
            .andDo(print()).andExpect(status().isBadRequest())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.responseCode").value(String.valueOf(ServerResponseConstants.API_FAILURE_CODE)));
    }

    @Test
    void whenSearchWithMalformedJson_thenGetErrorResponse() throws Exception {
        this.mockMvc.perform(post("/customer/fpn-search")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content("bad request"))
            .andDo(print()).andExpect(status().isBadRequest())
            .andExpect(content().contentType("application/json"))
            .andExpect(jsonPath("$.responseCode").value(String.valueOf(ServerResponseConstants.API_FAILURE_CODE)));
    }

    public FixedPenaltyNotice getFpnEntityStub() {
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

    public Offence getOffenceStub() {
        Offence offence = new Offence();
        offence.setTitle("Railway Safety Act 2005, Part 15, Section 129");
        offence.setDescription("Failure of refusal to give name or address...");
        offence.setAppealLetterText("You were in breach of Railway Safety Act 2005...");
        offence.setAmount(BigDecimal.valueOf(100.0));
        return offence;
    }
}
