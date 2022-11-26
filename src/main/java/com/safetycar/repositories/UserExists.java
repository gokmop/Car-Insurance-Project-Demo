package com.safetycar.repositories;

import com.safetycar.models.users.UserDetails;

public interface UserExists {

    boolean existsByUserDetails(UserDetails userDetails);

}
