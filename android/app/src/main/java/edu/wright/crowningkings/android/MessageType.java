package edu.wright.crowningkings.android;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by eric on 11/13/15. Modified by csmith on 11/17/16 for CrowningKings
 */
public enum MessageType implements Parcelable {
    SENT(0),
    RECEIVED(1);

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MessageType createFromParcel(Parcel in) {
            return MessageType.values()[in.readInt()];
        }

        public MessageType[] newArray(int size) {
            return new MessageType[size];
        }
    };
    private int messageNum;

    MessageType(int messageNum) {
        this.messageNum = messageNum;
    }

    MessageType(Parcel in) {
        this.messageNum = in.readInt();
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

