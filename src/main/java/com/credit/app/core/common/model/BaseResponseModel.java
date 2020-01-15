package com.credit.app.core.common.model;

import com.credit.app.core.common.constants.CustomStatusCodes;
import com.credit.app.core.common.constants.Messages;
import lombok.Data;

@Data
public class BaseResponseModel {
    private int statusCode = CustomStatusCodes.FAILURE;
    private String message = Messages.BASE_ERROR;
}
