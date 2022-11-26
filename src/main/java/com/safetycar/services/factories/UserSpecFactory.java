package com.safetycar.services.factories;

import com.safetycar.models.users.User;
import com.safetycar.repositories.filter.base.MapBasedSpecification;

import java.util.Map;

public interface UserSpecFactory {

    MapBasedSpecification<User> getUserSpec(Map<String, String> filter);

    MapBasedSpecification<User> getUserSpec();
}
