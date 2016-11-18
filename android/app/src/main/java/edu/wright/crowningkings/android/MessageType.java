package edu.wright.crowningkings.android;

/**
 * Created by eric on 11/13/15. Modified by csmith on 11/17/16 for CrowningKings
 */
public enum MessageType {
    SENT (0),
    RECEIVED (1);

    private int messageNum;

    MessageType(int messageNum) {
        this.messageNum = messageNum;
    }

    public int getVal() {
        return this.messageNum;
    }
}

