package com.credit.app.core.service;

import com.credit.app.core.common.model.UserInfo;
import com.credit.app.core.exception.AppServiceException;
import com.credit.app.core.persist.User;

import java.util.List;


public interface IUserService extends IBaseService<User, UserInfo> {

    User getByUsername(String username) throws AppServiceException;

    String signup(UserInfo info) throws AppServiceException;

    String login(UserInfo info) throws AppServiceException;

    List<UserInfo> getListByStatus(Integer code) throws AppServiceException;

}
