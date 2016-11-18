package edu.wright.crowningkings.android;

/**
 * Created by eric on 11/13/15. Modified by csmith on 11/17/16 for CrowningKings
 */
public class Message {
    public String text;
    public long date;
    public String id;
    public String handleID;
    public long cROWID;
    MessageType messageType;
    MessageStatus messageStatus;

    public Message(String text) {
        this(text, MessageType.RECEIVED, MessageStatus.UNSUCCESSFUL, "unknown", -1);
    }

    public Message(String text, MessageType messageType, MessageStatus messageStatus, String handleID) {
        this(text, messageType, messageStatus, handleID, -1);
    }

    public Message(String text, MessageType messageType, MessageStatus messageStatus, String handleID, long date) {
        this.text = text;
        this.messageType = messageType;
        this.messageStatus = messageStatus;
        this.handleID = handleID;
        this.date = date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setHandleID(String handleID) {
        this.handleID = handleID;
    }

}
