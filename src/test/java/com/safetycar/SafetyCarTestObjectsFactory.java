package com.safetycar;

import com.safetycar.enums.PolicyStatuses;
import com.safetycar.models.*;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.web.dto.offer.OfferDto;
import com.safetycar.web.dto.policy.CreatePolicyDto;
import com.safetycar.web.dto.policy.ShowDetailedPolicyDto;
import com.safetycar.web.dto.policy.ShowPolicyDto;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.CreateUserDto;
import com.safetycar.web.dto.user.ShowUserDto;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import static com.safetycar.security.MySpringUserDetailsService.getAuthorities;
import static com.safetycar.util.Helper.getUserRoles;

public final class SafetyCarTestObjectsFactory {

    private static final String TEST_ADDRESS = "testAddress";
    private static final String TEST_FIRST_NAME = "testFirstName";
    private static final String TEST_LAST_NAME = "testLastName";
    private static final String TELEPHONE = "12321432";
    private static final String TEST_MAIL_COM = "test@mail.com";
    private static final String PASSWORD = "password";

    private SafetyCarTestObjectsFactory() {
    }

    public static UserDetails getUserDetails(User user) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName(user.getUserName());
        userDetails.setAddress(TEST_ADDRESS);
        userDetails.setFirstName(TEST_FIRST_NAME);
        userDetails.setLastName(TEST_LAST_NAME);
        userDetails.setTelephone(TELEPHONE);
        userDetails.setActive(true);
        userDetails.setIntegerId(0);
        return userDetails;
    }

    public static User getUser() {
        User user = new User();
        user.setUsername(TEST_MAIL_COM);
        user.setUserDetails(getUserDetails(user));
        user.setPassword(PASSWORD);
        user.setRoles(getUserRoles(user));
        user.setEnabled(true);
        return user;
    }

    public static User getUser(String anotherName) {
        User user = new User();
        user.setUsername(anotherName);
        user.setUserDetails(getUserDetails(user));
        user.setPassword(anotherName);
        user.setRoles(getUserRoles(user));
        user.setEnabled(true);
        return user;
    }

    public static VerificationToken getToken(User user) {
        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        return token;
    }

    public static VerificationToken getExpiredToken(User user) {
        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        long nowMilliSec = System.currentTimeMillis();
        token.setExpiryDate(new Date(nowMilliSec));
        return token;
    }

    public static org.springframework.security.core.userdetails.User getSpringUser(User safetyCarUser) {
        return new org.springframework.security.core.userdetails.User(safetyCarUser.getUserName(),
                safetyCarUser.getPassword(),
                !safetyCarUser.isDisabled(),
                true,
                true,
                true,
                getAuthorities(safetyCarUser.getRoles()));
    }

    public static Coefficient getCoefficient() {
        Coefficient coefficient = new Coefficient();
        coefficient.setAccidentRisk(1);
        coefficient.setAgeRisk(1);
        coefficient.setTax(1);
        return coefficient;
    }

    public static BaseAmount getBaseAmount() {
        BaseAmount baseAmount = new BaseAmount();
        baseAmount.setBaseAmount(BigDecimal.valueOf(1000));
        baseAmount.setCarAgeMax(0);
        baseAmount.setCarAgeMax(25);
        baseAmount.setCcMax(1125);
        baseAmount.setCcMin(0);
        return baseAmount;
    }

    public static Model getModel() {
        Model model = new Model();
        model.setBrand(getBrand());
        model.setName("testModel");
        model.setYear(1900);
        model.setId(1);
        return model;
    }

    public static Brand getBrand() {
        Brand brand = new Brand();
        brand.setId(1);
        brand.setName("testBrand");
        return brand;
    }

    public static Car getCar() {
        Car car = new Car();
        car.setId(1);
        car.setCapacity(1000);
        car.setFirstRegistration(LocalDate.now());
        car.setModelYearAndBrand(getModel());
        return car;
    }

    public static Offer getOffer() {
        BaseAmount baseAmount = getBaseAmount();
        Offer offer = new Offer();
        offer.setCar(getCar());
        offer.setAboveTwentyFive(true);
        offer.setHadAccidents(false);
        offer.setPremium(baseAmount.getBaseAmount());
        return offer;
    }

    public static Offer getOffer(boolean testFlags) {
        BaseAmount baseAmount = getBaseAmount();
        Offer offer = new Offer();
        offer.setCar(getCar());
        offer.setAboveTwentyFive(testFlags);
        offer.setHadAccidents(testFlags);
        offer.setPremium(baseAmount.getBaseAmount());
        return offer;
    }

    public static CreateDetailsDto getDetailsDto(User user) {
        UserDetails details = user.getUserDetails();
        CreateDetailsDto dto = new CreateDetailsDto();
        dto.setAddress(details.getAddress());
        dto.setFirstName(details.getFirstName());
        dto.setLastName(details.getLastName());
        dto.setTelephone(details.getTelephone());
        return dto;
    }

    public static Image getImage() {
        Image image = new Image();
        image.setImageType("testImage");
        image.setData(new byte[]{1});
        image.setImageName("testImageName");
        return image;
    }

    public static Policy getPolicy(User user, Offer offer) {
        Policy policy = new Policy();
        policy.setCar(getCar());
        policy.setOwner(user);
        policy.setImage(getImage());
        policy.setOffer(offer);
        java.sql.Date startDate = new java.sql.Date(System.currentTimeMillis());
        policy.setStartDate(startDate);
        LocalDate localEndDate = startDate.toLocalDate().plusYears(1);
        policy.setEndDate(java.sql.Date.valueOf(localEndDate));

        policy.setStatus(getStatus());
        return policy;
    }

    public static OfferDto getOfferDto(Offer offer,
                                       boolean aboveTwentyFive,
                                       boolean hadAccidents) {
        OfferDto dto = new OfferDto();
        dto.setAboveTwentyFive(String.valueOf(aboveTwentyFive));
        dto.setAboveTwentyFive(String.valueOf(hadAccidents));
        Car car = offer.getCar();
        dto.setCapacity(String.valueOf(car.getCapacity()));
        Model model = car.getModelYearBrand();
        Brand brand = model.getBrand();
        dto.setMakeId(brand.getId());
        dto.setModelId(model.getId());
        dto.setDateRegistered(car.getFirstRegistration());
        return dto;
    }

    public static ShowUserDto getShowUserDto(User user) {
        UserDetails userDetails = user.getUserDetails();
        ShowUserDto dto = new ShowUserDto();
        dto.setFullName(userDetails.getFullName());
        dto.setSimulatedOffers(userDetails.getOffersCount());
        dto.setUserId(userDetails.getIntegerId());

        dto.setApprovedCount(userDetails.getApprovedPoliciesCount());
        dto.setPendingCount(userDetails.getPendingPoliciesCount());

        dto.setApprovedNet(userDetails.getApprovedPoliciesNet());
        dto.setPendingNet(userDetails.getPendingPoliciesNet());
        return dto;
    }

    public static CreateDetailsDto getCreateDetailsDto(User user) {
        CreateDetailsDto dto = new CreateDetailsDto();
        UserDetails details = user.getUserDetails();
        dto.setId(details.getIntegerId());
        dto.setTelephone(details.getTelephone());
        dto.setAddress(details.getAddress());
        dto.setFirstName(details.getFirstName());
        dto.setLastName(details.getLastName());
        return dto;
    }

    public static CreateUserDto getCreateUserDto(User user) {
        CreateUserDto dto = new CreateUserDto();
        dto.setPassword(user.getPassword());
        dto.setConfirmationPassword(user.getPassword());
        dto.setEmail(user.getUserName());
        return dto;
    }

    public static History getHistory(User actor) {
        History history = new History();
        history.setActor(actor.getUserDetails());
        history.setAction("action");
        history.setHistory("history");
        return history;
    }

    public static CreatePolicyDto getCreatePolicyDto(Policy policy) {
        CreatePolicyDto dto = new CreatePolicyDto();
        dto.setStartDate(new java.sql.Date(policy.getStartDate().getTime()).toLocalDate());
        dto.setFile(new MockMultipartFile("name", "originalName", "type", new byte[]{}));
        return dto;
    }

    public static ShowPolicyDto getShowPolicyDto(Policy policy) {

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

    public static ShowDetailedPolicyDto getShowDetailedPolicyDto(Policy policy) {
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

    public static Status getStatus() {
        Status status = new Status();
        status.setStatus(PolicyStatuses.PENDING.toString());
        return status;
    }

    public static MultipartFile getFile() {
        return new MockMultipartFile("test", "testImageName", "testImage", new byte[]{1});
    }

    public static MultipartFile getFile(Image image) {
        return new MockMultipartFile("test", image.getImageName(), image.getImageType(), image.getData());
    }

}
