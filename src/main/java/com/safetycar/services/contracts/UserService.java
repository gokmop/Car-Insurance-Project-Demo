package com.safetycar.services.contracts;

import com.safetycar.models.users.User;
import com.safetycar.models.users.UserDetails;
import com.safetycar.services.contracts.base.*;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Map;

public interface UserService extends
        CreateService<User>, UpdateService<User>, GetAll<User>, GetService {

    boolean existsByMail(String email);

    User userByEmail(String email);

    User getUserByToken(String verificationToken);

    UserDetails getUserDetails(Integer id);

    List<UserDetails> getAllDetails();

    List<UserDetails> searchDetails(Map<String, String> filter);

}
