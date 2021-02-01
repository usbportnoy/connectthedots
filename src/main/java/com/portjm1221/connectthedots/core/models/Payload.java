package com.portjm1221.connectthedots.core.models;

public class Payload {
    private String msg;
    private Object body;

    public Payload() {
    }

    public Payload(String msg, Object body) {
        this.msg = msg;
        this.body = body;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
