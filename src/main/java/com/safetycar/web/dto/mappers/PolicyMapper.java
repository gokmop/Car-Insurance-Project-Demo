package com.safetycar.web.dto.mappers;

import com.safetycar.enums.PolicyStatuses;
import com.safetycar.models.Image;
import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.StatusService;
import com.safetycar.web.dto.policy.CreatePolicyDto;
import com.safetycar.web.dto.policy.RestCreatePolicyDto;
import com.safetycar.web.dto.policy.ShowDetailedPolicyDto;
import com.safetycar.web.dto.policy.ShowPolicyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Component
public class PolicyMapper {

    private final ImageMapper imageMapper;
    private final StatusService statusService;

    @Autowired
    public PolicyMapper(ImageMapper imageMapper,
                        StatusService statusService) {
        this.imageMapper = imageMapper;
        this.statusService = statusService;
    }

    public Policy assemble(Offer offer, CreatePolicyDto dto, User owner) {
        Image image = imageMapper.fromMultipart(dto.getFile());
        Policy policy = new Policy();
        policy.setImage(image);
        policy.setOffer(offer);
        policy.setOwner(owner);
        policy.setCar(offer.getCar());
        policy.setStatus(statusService.getOne(PolicyStatuses.PENDING.getId()));
        policy.setStartDate(java.sql.Date.valueOf(dto.getStartDate()));
        LocalDate localEndDate = dto.getStartDate().plusYears(1);
        policy.setEndDate(java.sql.Date.valueOf(localEndDate));
        return policy;
    }

    public ShowPolicyDto toDto(Policy policy) {

        ShowPolicyDto dto = new ShowPolicyDto();

        dto.setPolicyId(policy.getId());
        dto.setOwnerId(policy.getOwner().getUserDetails().getIntegerId());
        dto.setCarMake(policy.getCar().getModelYearBrand().getBrand().getName());
        dto.setCarModel(policy.getCar().getModelYearBrand().getName());
        dto.setOfferId(policy.getOffer().getId());
        dto.setPolicyPremium(policy.getOffer().getPremium());
        dto.setCarFirstRegistration(policy.getCar().getFirstRegistration().toString());
        dto.setSubmissionDate(policy.getSubmissionDate().toString());
        dto.setStartDate(policy.getSubmissionDate().toString());
        dto.setEndDate(policy.getEndDate().toString());
        dto.setStatus(policy.getStatus().getStatus());
        dto.setHistoryList(policy.getHistories());

        return dto;
    }

    public List<ShowPolicyDto> toDto(Iterable<Policy> policies) {
        List<ShowPolicyDto> dto = new LinkedList<>();
        policies.forEach(p -> dto.add(this.toDto(p)));
        return dto;
    }

    public ShowDetailedPolicyDto toDetailedDto(Policy policy) {
        ShowDetailedPolicyDto dto = new ShowDetailedPolicyDto();

        dto.setPolicyId(policy.getId());
        dto.setOwnerId(policy.getOwner().getUserDetails().getIntegerId());
        dto.setOwnerFirstName(policy.getOwner().getUserDetails().getFirstName());
        dto.setOwnerLastName(policy.getOwner().getUserDetails().getLastName());
        dto.setOwnerTelephone(policy.getOwner().getUserDetails().getTelephone());
        dto.setOwnerAddress(policy.getOwner().getUserDetails().getAddress());
        dto.setCarMake(policy.getCar().getModelYearBrand().getBrand().getName());
        dto.setCarModel(policy.getCar().getModelYearBrand().getName());
        dto.setCarFirstRegistration(policy.getCar().getFirstRegistration().toString());
        dto.setOfferId(policy.getOffer().getId());
        dto.setAccidentsInPreviousYear(policy.getOffer().hadAccidents());
        dto.setOwnerAbove25Years(policy.getOffer().isAboveTwentyFive());
        dto.setPolicyPremium(policy.getOffer().getPremium());
        dto.setSubmissionDate(policy.getSubmissionDate().toString());
        dto.setStartDate(policy.getStartDate().toString());
        dto.setEndDate(policy.getEndDate().toString());
        return dto;
    }

    public Policy fromRestCreatePolicyDto(Offer offer, User user, RestCreatePolicyDto dto){
        Image image = new Image();

        Policy policy = new Policy();

        policy.setImage(image);
        policy.setOffer(offer);
        policy.setOwner(user);
        policy.setCar(offer.getCar());
        policy.setStatus(statusService.getOne(PolicyStatuses.PENDING.getId()));
        policy.setStartDate(java.sql.Date.valueOf(dto.getStartDate()));
        LocalDate localEndDate = dto.getStartDate().plusYears(1);
        policy.setEndDate(java.sql.Date.valueOf(localEndDate));

        return policy;
    }

}
