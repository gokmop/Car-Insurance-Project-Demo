package com.safetycar.services.contracts.base;

public interface CrudService<T, ID> extends GetOne<T, ID>, GetAll<T>,
        CreateService<T>, UpdateService<T>, DeleteService<T> {
}
