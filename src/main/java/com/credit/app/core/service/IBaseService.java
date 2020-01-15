package com.credit.app.core.service;

import com.credit.app.core.common.model.BaseInfo;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.persist.BaseEntity;

import java.util.List;


public interface IBaseService<E extends BaseEntity<I>, I extends BaseInfo> {

    I getById(Long id) throws AppServiceException;

    I create(I info) throws AppServiceException;

    I update(I info) throws AppServiceException;

    void delete(Long id) throws AppServiceException;

    List<I> getList() throws AppServiceException;

}
