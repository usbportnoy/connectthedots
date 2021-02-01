package com.portjm1221.connectthedots.core.models;

public class StateUpdate {
    private Line newLine;
    private String heading;
    private String message;

    public StateUpdate() {
    }

    public StateUpdate(Line newLine, String heading, String message) {
        this.newLine = newLine;
        this.heading = heading;
        this.message = message;
    }

    public Line getNewLine() {
        return newLine;
    }

    public void setNewLine(Line newLine) {
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
