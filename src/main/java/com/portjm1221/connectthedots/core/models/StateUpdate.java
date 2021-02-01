package com.portjm1221.connectthedots.core.models;

public class StateUpdate {
    private String newLine;
    private String heading;
    private String message;

    public StateUpdate() {
    }

    public StateUpdate(String newLine, String heading, String message) {
        this.newLine = newLine;
        this.heading = heading;
        this.message = message;
    }

    public String getNewLine() {
        return newLine;
    }

    public void setNewLine(String newLine) {
        this.newLine = newLine;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
