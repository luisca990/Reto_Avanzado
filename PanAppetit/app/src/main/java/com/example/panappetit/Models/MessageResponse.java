package com.example.panappetit.Models;

public class MessageResponse {
    private Integer Code;
    private String Message;

    public MessageResponse() {
    }

    public MessageResponse(Integer code, String message) {
        this.Code = code;
        this.Message = message;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        this.Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        this.Message = message;
    }
}
