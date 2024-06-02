package com.aminkbi.learnspring.models.response;

public class UpdateResponse implements Response{

    private final Integer resCode;
    private final String resMessage;

    public UpdateResponse(Integer resCode, String resMessage) {
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
