package com.psvmchannel.springaop.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomStatus {

    SUCCESS(0, "Success"),
    NOT_FOUND(1, "Not found"),
    EXCEPTION(2, "Exception");

    private final int code;
    private final String message;
}