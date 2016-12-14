package com.pheth.hasee.stickerhero.BaseData.Data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allengotstuff on 12/5/2016.
 */
public class DataContainer implements BaseData, Parcelable {

    String name="";
    String identifier="";
    String localThumbUrl="";
    String onlineThumbUrl="";
    String localFullUrl="";
    String onLineFullUrl="";
    boolean isAnimateable = false;

    public DataContainer(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setIdentifier(String id) {
        this.identifier = id;
    }

    public void setLocalThumbUrl(String url) {
        this.localThumbUrl = url;
    }

    public void setOnlineThumbUrl(String on_url) {
        this.onlineThumbUrl = on_url;
    }

    public void setLocalFullUrl(String url) {
        this.localFullUrl = url;
    }

    public void setOnLineFullUrl(String on_url) {
        this.onLineFullUrl = on_url;
    }

    public void setIsAnimateable(boolean animateable){
        this.isAnimateable = animateable;
    }

    @Override
    public boolean getIsAnimateable(){
        return this.isAnimateable;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getLocalThumbUrl() {
        return this.localThumbUrl;
    }

    @Override
    public String getOnlineThumbUrl() {
        return this.onlineThumbUrl;
    }

    @Override
    public String getLocalFullUrl() {
        return this.localFullUrl;
    }

    @Override
    public String getOnlineFullUrl() {
        return this.localFullUrl;
    }


    /********************序列化方法*************************/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(identifier);
        dest.writeString(localThumbUrl);
        dest.writeString(onlineThumbUrl);
        dest.writeString(localFullUrl);
        dest.writeString(onLineFullUrl);

        dest.writeByte((byte) (isAnimateable ? 1 : 0));
    }

    // Using the `in` variable, we can retrieve the values that
    // we originally wrote into the `Parcel`.  This constructor is usually
    // private so that only the `CREATOR` field can access.
    private DataContainer(Parcel in) {
        name = in.readString();
        identifier = in.readString();
        localThumbUrl = in.readString();
        onlineThumbUrl = in.readString();
        localFullUrl = in.readString();
        onLineFullUrl = in.readString();
        isAnimateable = in.readByte() != 0;
    }


    public static final Parcelable.Creator<DataContainer> CREATOR
            = new Parcelable.Creator<DataContainer>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public DataContainer createFromParcel(Parcel in) {
            return new DataContainer(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public DataContainer[] newArray(int size) {
            return new DataContainer[size];
        }
    };
}
