package com.safetycar.services;

import com.safetycar.models.Status;
import com.safetycar.repositories.StatusRepository;
import com.safetycar.services.contracts.StatusService;
import com.safetycar.services.contracts.base.GetServiceBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class StatusServiceImpl extends GetServiceBase implements StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<Status> getAll() {
        return statusRepository.findAll();
    }

    @Override
    public Status getOne(Integer integer) {
        return get(statusRepository, integer, Status.class);
    }
}
