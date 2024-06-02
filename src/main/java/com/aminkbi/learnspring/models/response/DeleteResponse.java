package com.aminkbi.learnspring.models.response;

public class DeleteResponse implements Response{

    private final Integer resCode;
    private final String resMessage;

    public DeleteResponse(Integer resCode, String resMessage) {
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
