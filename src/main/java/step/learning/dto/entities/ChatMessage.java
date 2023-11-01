package step.learning.dto.entities;

import java.util.Date;

public class ChatMessage
{
    private String user;
    private String message;
    private Date moment;

    public ChatMessage() { }
    public ChatMessage(String user, String message) {
        this.user = user;
        this.message = message;
    }

    //#region accessors
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Date getMoment() {
        return moment;
    }
    //#endregion
}
