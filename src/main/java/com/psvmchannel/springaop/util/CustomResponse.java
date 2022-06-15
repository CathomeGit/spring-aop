package com.psvmchannel.springaop.util;

import lombok.Getter;

import java.util.Collection;

@Getter
public class CustomResponse<T> {

    private final int code;
    private final String message;
    private final Collection<T> responseList;

    public CustomResponse(Collection<T> responseList, CustomStatus customStatus) {
        this.responseList = responseList;
        code = customStatus.getCode();
        message = customStatus.getMessage();
    }
}