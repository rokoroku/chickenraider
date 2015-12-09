package com.bung.bungapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rok on 2015. 11. 14..
 */
public class User implements Parcelable {
    int id;
    String username;
    String last_name;

    public User(String username, String name) {
        this.username = username;
        this.last_name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return last_name;
    }

    public void setName(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.username);
        dest.writeString(this.last_name);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.username = in.readString();
        this.last_name = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
