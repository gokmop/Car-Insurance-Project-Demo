package com.safetycar.services.contracts;

import com.safetycar.models.Policy;
import com.safetycar.models.users.User;

public interface PolicyHistoryService {

    void appendHistory(User user, Policy policy, String action, String message);

}
