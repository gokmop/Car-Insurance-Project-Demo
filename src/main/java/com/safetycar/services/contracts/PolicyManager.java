package com.safetycar.services.contracts;

import com.safetycar.models.Offer;
import com.safetycar.models.Policy;
import com.safetycar.models.Status;
import com.safetycar.models.users.User;

public interface PolicyManager {

    void adminUpdatePolicy(Policy policy, Status status, User agent, String message);

    void createPolicy(Policy policy, Offer offer, User owner);

    void userUpdatePolicy(Policy policy, User owner);

}
