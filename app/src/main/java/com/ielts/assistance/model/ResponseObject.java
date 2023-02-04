package com.ielts.assistance.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseObject implements Serializable {
    private String status;
    private String message;
    private Object data;

    public ResponseObject(String message) {
        this.message = message;
    }

    public ResponseObject(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseObject(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseObject(Object data) {
        this.data = data;
    }
}

