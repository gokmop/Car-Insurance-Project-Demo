package com.safetycar.services;

import com.safetycar.models.VerificationToken;
import com.safetycar.models.users.User;
import com.safetycar.repositories.RoleRepository;
import com.safetycar.repositories.TokenRepository;
import com.safetycar.repositories.UserRepository;
import com.safetycar.services.contracts.ExpiredTokenDisposal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;

@Component
public class ExpiredTokenDisposalImpl implements ExpiredTokenDisposal {

    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ExpiredTokenDisposalImpl(TokenRepository tokenRepository,
                                    RoleRepository roleRepository,
                                    UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void clearExpired(VerificationToken verificationToken) {
        User user = verificationToken.getUser();
        tokenRepository.delete(verificationToken);
        if (user.isDisabled()) {
            roleRepository.deleteAuthority(user.getUserName());
            user.setRoles(new HashSet<>());
            userRepository.save(user);
            userRepository.delete(user);
        }
    }
}
