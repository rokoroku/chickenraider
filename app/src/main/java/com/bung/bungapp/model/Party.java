package com.bung.bungapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by rok on 2015. 11. 14..
 */
public class Party implements Parcelable {

    /**
     * name : 롤 팀랭하실분
     * latitude : 37.6176651
     * limit_num : 4
     * creator : 1
     * longitude : 126.8338831
     * description : 곧 시즌보상도 다가오고 하니까 같이 팀랭 돌리실분
     * pub_date : 2015-11-13
     */

    private int id;
    private String name;
    private String description;
    private String place_id;
    private String place_name;
    private int number_of_participants;
    private int limit_num = 8;
    private double latitude;
    private double longitude;
    private User creator;

    public Party(String name, String description, String placeName, String placeId, double latitude, double longitude) {
        this.name = name;
        this.description = description;
        this.place_name = placeName;
        this.place_id = placeId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLimit(int limit) {
        this.limit_num = limit;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getLimit() {
        return limit_num;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public double getLongitude() {
        return longitude;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }

    public String getDescription() {
        return description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeString(this.place_id);
        dest.writeInt(this.limit_num);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeParcelable(this.creator, flags);
    }

    protected Party(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.place_id = in.readString();
        this.limit_num = in.readInt();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.creator = in.readParcelable(User.class.getClassLoader());
        long tmpPub_date = in.readLong();
    }

    public static final Creator<Party> CREATOR = new Creator<Party>() {
        public Party createFromParcel(Parcel source) {
            return new Party(source);
        }

        public Party[] newArray(int size) {
            return new Party[size];
        }
    };

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public int getNumber_of_participants() {
        return number_of_participants;
    }

    public void setNumber_of_participants(int number_of_participants) {
        this.number_of_participants = number_of_participants;
    }
}
