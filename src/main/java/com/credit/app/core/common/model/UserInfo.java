package com.credit.app.core.common.model;

import com.credit.app.core.common.ref.UserStatus;
import lombok.Data;


@Data
public class UserInfo extends BaseInfo {
    private String username;
    private String password;
    private String name;
    private String surname;
    private UserStatus status;
}
