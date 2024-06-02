package com.aminkbi.learnspring.models.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class ResponseModel<T> implements Response {

    private Integer resCode;
    private String resMessage;
    private T info;

    public ResponseModel(Integer resCode, String resMessage, T info){
        this.resCode = resCode;
        this.resMessage = resMessage;
        this.info = info;
    }

}