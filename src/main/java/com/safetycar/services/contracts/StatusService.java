package com.safetycar.services.contracts;

import com.safetycar.models.Status;
import com.safetycar.services.contracts.base.GetAll;
import com.safetycar.services.contracts.base.GetOne;

public interface StatusService extends GetAll<Status>, GetOne<Status,Integer> {
}
