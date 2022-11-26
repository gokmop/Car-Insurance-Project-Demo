package com.safetycar.web.dto.mappers;


import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.web.dto.user.CreateDetailsDto;
import com.safetycar.web.dto.user.CreateUserDto;
import com.safetycar.web.dto.user.RestShowUserDto;
import com.safetycar.web.dto.user.ShowUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static com.safetycar.util.Helper.getUserRoles;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User fromDto(CreateUserDto dto) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName(dto.getEmail());
        User user = new User();
        user.setUsername(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(getUserRoles(user));
        user.setUserDetails(userDetails);
        return user;
    }

    public UserDetails fromDto(CreateDetailsDto dto, UserDetails userDetails) {
        userDetails.setAddress(dto.getAddress());
        userDetails.setFirstName(dto.getFirstName());
        userDetails.setLastName(dto.getLastName());
        userDetails.setTelephone(dto.getTelephone());
        userDetails.setActive(true);
        return userDetails;
    }

    public CreateDetailsDto toDto(User user) {
        CreateDetailsDto dto = new CreateDetailsDto();
        UserDetails details = user.getUserDetails();
        dto.setId(details.getIntegerId());
        dto.setTelephone(details.getTelephone());
        dto.setAddress(details.getAddress());
        dto.setFirstName(details.getFirstName());
        dto.setLastName(details.getLastName());
        return dto;
    }

    public ShowUserDto toDto(UserDetails userDetails) {
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

    public Collection<ShowUserDto> toDto(Iterable<UserDetails> detailsCollection) {
        List<ShowUserDto> dtos = new LinkedList<>();
        for (UserDetails userDetails : detailsCollection) {
            dtos.add(this.toDto(userDetails));
        }
        return dtos;
    }

    public RestShowUserDto restShowUserDto (UserDetails details){
        RestShowUserDto dto = new RestShowUserDto();
        dto.setUserId(details.getIntegerId());
        dto.setFullName(details.getFullName());
        dto.setAddress(details.getAddress());
        dto.setTelephone(details.getTelephone());
        dto.setUsername(details.getUserName());
        return dto;
    }
}
