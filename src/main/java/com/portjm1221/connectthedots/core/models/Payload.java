package com.portjm1221.connectthedots.core.models;

import com.portjm1221.connectthedots.service.GameService;

public class Payload {
    private String msg;
    private Object body;

    public Payload() {
    }

    public Payload(String msg, Object body) {
        this.msg = msg;
        this.body = body;
    }

    public Payload(PayloadBuilder payloadBuilder) {
        this.msg = payloadBuilder.msg.toString();
        this.body = payloadBuilder.body;
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

    public static class PayloadBuilder{
        private Msg msg;
        private Object body;

        public PayloadBuilder messageIs(Msg msg){
            this.msg = msg;
            return this;
        }

        public PayloadBuilder say(String message){
            if(this.body == null){
                this.body = new StateUpdate();
            }
            ((StateUpdate)this.body).setMessage(message);
            return this;
        }

        public PayloadBuilder withHeading(String heading){
            if(this.body == null){
                this.body = new StateUpdate();
            }
            ((StateUpdate)this.body).setHeading(heading);
            return this;
        }

        public PayloadBuilder withPlayerHeading(int player){
            if(this.body == null){
                this.body = new StateUpdate();
            }
            ((StateUpdate)this.body).setHeading(GameService.getPlayerNameText(player));
            return this;
        }

        public PayloadBuilder add(Line newLine){
            if(this.body == null){
                this.body = new StateUpdate();
            }
            ((StateUpdate)this.body).setNewLine(newLine);
            return this;
        }

        public Payload build(){
            //todo needs validation
            return new Payload(this);
        }

    }
}
