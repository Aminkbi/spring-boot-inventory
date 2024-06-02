package com.aminkbi.learnspring.models.response;

public class ErrorResponse implements Response {
    private final Integer resCode;
    private final String resMessage;

    public ErrorResponse(Integer resCode, String resMessage) {
        this.resCode = resCode;
        this.resMessage = resMessage;
    }

    @Override
    public Integer getResCode() {
        return resCode;
    }

    @Override
    public String getResMessage() {
        return resMessage;
    }
}