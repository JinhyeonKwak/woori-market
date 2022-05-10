package com.mayy5.admin.common;

// For Business Logic
public enum BError implements Error {
    REQUIRED("REQUIRED", "%1 is a required.."),
    NOT_EXIST("NOT_EXIST", "%1 does not exist."),
    EXIST("EXIST", "%1 is exist."),
    NOT_MATCH("NOT_MATCH", "%1 does not match."),
    NOT_VALID("NOT_VALID", "%1 is not valid."),
    NOT_MATCHS("NOT_MATCHS", "%1 and %2 do not match."),
    MATCH("MATCH", "%1 match,"),
    MATCHS("MATCHS", "%1 and %2 match,"),
    FAIL("FAIL", "%1 failed."),
    SUCCEESS("SUCCEESS", "%1 succeeded."),
    FAIL_REASON("FAIL_REASON", "%1 failed for reason (%2)."),
    NOT_SUPPORT("NOT_SUPPORT", "%1 not supported."),
    NOT_REGISTERD("NOT_REGISTERD", "%1 not registered.");

    private String errCode;
    private String msg;

    @Override public String getCode() {
        return this.errCode;
    }
    @Override public String getMessage(String... args) {
        return ErrMsgUtil.parseMessage(this.msg, args);
    }

    BError(String errCode, String msg) {
        this.errCode = errCode;
        this.msg = msg;
    }
}

