package com.schoolmanagement.poc.service.interfaces;

import com.schoolmanagement.poc.model.ResponseModel;

public interface ICrudService<T, R, P> {

    R create(T requestModel);

    P findAll();

    R findById(String id);

    R update(T requestModel, String id);

    ResponseModel delete(String id);
}
