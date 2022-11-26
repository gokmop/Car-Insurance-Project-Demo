package com.safetycar.services;

import com.safetycar.exceptions.AccountNotActivatedException;
import com.safetycar.exceptions.DuplicateEntityException;
import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.repositories.UserDetailsRepository;
import com.safetycar.repositories.UserRepository;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.services.contracts.UserService;
import com.safetycar.services.contracts.VerificationTokenService;
import com.safetycar.services.contracts.base.GetService;
import com.safetycar.services.contracts.base.GetServiceBase;
import com.safetycar.services.factories.UserSpecFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.safetycar.repositories.filter.UserSpec.ACTIVE;
import static com.safetycar.repositories.filter.UserSpec.TRUE;
import static com.safetycar.util.Constants.ErrorsConstants.*;
import static com.safetycar.util.Constants.Views.QueryConstants.ID;
import static com.safetycar.util.Constants.Views.QueryConstants.SORT_PARAMETER;

@Service
public class UserServiceImpl extends GetServiceBase implements UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository detailsRepository;
    private final VerificationTokenService tokenService;
    private final UserSpecFactory specFactory;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           UserDetailsRepository detailsRepository,
                           VerificationTokenService tokenService,
                           UserSpecFactory specFactory) {
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
        this.tokenService = tokenService;
        this.specFactory = specFactory;
    }

    @Override
    public void create(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new DuplicateEntityException(EMAIL_ALREADY_EXISTS);
        }
        userRepository.save(user);
    }

    @Override
    public User userByEmail(String email) {
        User byUsername = get(userRepository, email, NO_USER_LIKE_THIS_WAS_FOUND);
        return validatedUser(byUsername);
    }

    @Override
    public void update(User user) {
        noDuplicate(user);
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByToken(String token) {
        VerificationToken verificationToken = tokenService.getVerificationToken(token);
        return verificationToken.getUser();
    }

    private User validatedUser(User user) {
        if (user.isDisabled()) {
            throw new AccountNotActivatedException();
        }
        return user;
    }

    @Override
    public boolean existsByMail(String email) {
        return userRepository.existsByUserName(email);
    }

    @Override
    public UserDetails getUserDetails(Integer id) {
        return get(detailsRepository, id, UserDetails.class);
    }

    @Override
    public List<UserDetails> getAllDetails() {
        MapBasedSpecification<User> spec = specFactory.getUserSpec();
        spec.addSpec(SORT_PARAMETER, ID);
        spec.addSpec(ACTIVE,TRUE);
        Sort sort = spec.getSort();
        return userRepository.findAll(spec, sort).stream()
                .map(User::getUserDetails)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDetails> searchDetails(Map<String, String> filter) {
        MapBasedSpecification<User> spec = specFactory.getUserSpec(filter);
        Sort sort = spec.getSort();
        return userRepository.findAll(spec, sort).stream()
                .map(User::getUserDetails)
                .collect(Collectors.toList());
    }

    private void noDuplicate(User user) {
        if (userRepository.existsByUserDetails(user.getUserDetails())) {
            throw new DuplicateEntityException(DUPLICATE_DETAILS_ERROR);
        }
    }


}
