package com.example.ecommerceinventory.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GenericResponse {
    private String status;
    private String message;
    private String errorCode;
    private Object data;

    public GenericResponse success(String msg, Object dt) {
        this.status = "success";
        this.message = msg;
        this.data = dt;
        return this;
    }

    public GenericResponse failure(String msg, String errCode) {
        this.status = "failure";
        this.message = msg;
        this.errorCode = errCode;
        return this;
    }

}
