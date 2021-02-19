package com.smi.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Dashboard  implements Parcelable {

    private String ammount;

    @Expose
    private String pic;

    @Expose
    private String name;

    @Expose
    private boolean isNew;


    public Dashboard(String pic, String ammount, String name) {
        this.pic = pic;
        this.ammount = ammount;
        this.name = name;
    }

    public Dashboard(String pic, String name, boolean isNew) {
        this.pic = pic;
        this.name = name;
        this.isNew = isNew;
    }


    public String getAmmount() {
        return ammount;
    }


    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }


    @Override
    public String toString() {
        return "Dashboard{" +
                "pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", isNew=" + isNew +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ammount);
        dest.writeString(this.pic);
        dest.writeString(this.name);
        dest.writeByte(this.isNew ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.ammount = source.readString();
        this.pic = source.readString();
        this.name = source.readString();
        this.isNew = source.readByte() != 0;
    }

    protected Dashboard(Parcel in) {
        this.ammount = in.readString();
        this.pic = in.readString();
        this.name = in.readString();
        this.isNew = in.readByte() != 0;
    }

    public static final Creator<Dashboard> CREATOR = new Creator<Dashboard>() {
        @Override
        public Dashboard createFromParcel(Parcel source) {
            return new Dashboard(source);
        }

        @Override
        public Dashboard[] newArray(int size) {
            return new Dashboard[size];
        }
    };
}


