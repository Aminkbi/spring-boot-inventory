package com.aminkbi.springbootInventory.models.response;


import lombok.Data;

@Data
public class ResponseModel<T> {

    private Integer resCode;
    private String resMessage;
    private T info;

    public ResponseModel(Integer resCode, String resMessage, T info){
        this.resCode = resCode;
        this.resMessage = resMessage;
        this.info = info;
    }

}