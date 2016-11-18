package edu.wright.crowningkings.android;

/**
 * Created by eric on 11/18/15. Modified by csmith on 11/17/16 for CrowningKings
 */
public enum MessageStatus {

    SUCCESSFUL (0),
    UNSUCCESSFUL (1),
    IN_PROGRESS (3);

    private int messageNum;

    MessageStatus(int messageNum) {
        this.messageNum = messageNum;
    }

    public int getVal() {
        return this.messageNum;
    }
}
