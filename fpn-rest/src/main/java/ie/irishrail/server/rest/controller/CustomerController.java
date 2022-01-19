package ie.irishrail.server.rest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ie.irishrail.server.base.service.FpnService;
import ie.irishrail.server.rest.dto.FixedPenaltyNoticeDto;
import ie.irishrail.server.rest.dto.SearchFpnDto;
import ie.irishrail.server.rest.mappers.AddressMapper;
import ie.irishrail.server.rest.mappers.FixedPenaltyNoticeMapper;

@RestController
@RequestMapping(value = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private FpnService fpnService;

    @Autowired
    public void setFpnService(FpnService fpnService) {
        this.fpnService = fpnService;
    }

    @PostMapping("/fpn-search")
    public ResponseEntity<FixedPenaltyNoticeDto> searchFixedPenaltyNotice(@Valid @RequestBody SearchFpnDto searchFpnDto) {
        var fixedPenaltyNotice = fpnService.search(searchFpnDto.getFpnNumber(), searchFpnDto.getRnNumber());
        var fixedPenaltyNoticeDto = FixedPenaltyNoticeMapper.MAPPER.toDto(fixedPenaltyNotice);
        fixedPenaltyNoticeDto.setAddress(AddressMapper.MAPPER.toDto(fixedPenaltyNotice));
        return ResponseEntity.ok(fixedPenaltyNoticeDto);
    }
}
