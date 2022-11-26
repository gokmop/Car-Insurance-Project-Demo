package com.safetycar.services.factories;

import com.safetycar.models.users.User;
import com.safetycar.repositories.filter.base.MapBasedSpecification;
import com.safetycar.repositories.filter.UserSpec;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserSpecFactoryImpl implements UserSpecFactory {

    @Override
    public MapBasedSpecification<User> getUserSpec(Map<String, String> filter) {
        return new UserSpec(filter);
    }

    @Override
    public MapBasedSpecification<User> getUserSpec() {
        return new UserSpec();
    }
}
