package com.smi.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Dashboard {

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



}


