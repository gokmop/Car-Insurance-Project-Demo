package com.safetycar.services;

import com.safetycar.models.History;
import com.safetycar.models.Policy;
import com.safetycar.models.users.User;
import com.safetycar.services.contracts.PolicyHistoryService;
import org.springframework.stereotype.Service;

@Service
public class PolicyHistoryServiceImpl implements PolicyHistoryService {

    @Override
    public void appendHistory(User user, Policy policy, String action, String message) {
        History history = new History();
        history.setActor(user.getUserDetails());
        history.setHistory(message);
        history.setAction(action);
        policy.addHistory(history);
    }
}
