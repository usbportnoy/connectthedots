package com.portjm1221.connectthedots.core.models;

public enum Msg {
    Initialize("INITIALIZE"),
    ValidStartNode("VALID_START_NODE"),
    ValidEndNode("VALID_END_NODE"),
    GameOver("GAME_OVER"),
    InvalidStartNode("INVALID_START_NODE"),
    InvalidEndNode("INVALID_END_NODE"),
    ;

    private String msg;

    Msg(String msg) {
        this.msg = msg;
    }


    @Override
    public String toString() {
        return this.msg;
    }
}
