package edu.wright.crowningkings.android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eric on 11/18/15. Modified by csmith on 11/17/16 for CrowningKings
 */
public enum MessageStatus implements Parcelable {

    SUCCESSFUL(0),
    UNSUCCESSFUL(1),
    IN_PROGRESS(3);

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MessageStatus createFromParcel(Parcel in) {
            return MessageStatus.values()[in.readInt()];
        }

        public MessageStatus[] newArray(int size) {
            return new MessageStatus[size];
        }
    };
    private int messageNum;

    MessageStatus(int messageNum) {
        this.messageNum = messageNum;
    }

    public int getVal() {
        return this.messageNum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(messageNum);
    }
}
