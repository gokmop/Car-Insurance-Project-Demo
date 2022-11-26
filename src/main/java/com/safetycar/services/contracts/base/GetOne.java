package com.safetycar.services.contracts.base;

public interface GetOne<T, ID> {

    T getOne(ID id);

}
