package com.terceira.semana.accenture.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    private String message;
    private Integer statusCode;


    public ResponseDTO(String message, Integer statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }

    public ResponseDTO(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
