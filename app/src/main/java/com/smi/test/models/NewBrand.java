package com.smi.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class NewBrand implements Parcelable {

    @Expose
    private String pic;

    @Expose
    private String name;

    @Expose
    private boolean isNew;

    private boolean description;

    public NewBrand(String pic, String name, boolean isNew) {
        this.pic = pic;
        this.name = name;
        this.isNew = isNew;
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

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NewBrand{" +
                "pic='" + pic + '\'' +
                ", name='" + name + '\'' +
                ", isNew=" + isNew +
                ", description=" + description +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.pic);
        dest.writeString(this.name);
        dest.writeByte(this.isNew ? (byte) 1 : (byte) 0);
        dest.writeByte(this.description ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        this.pic = source.readString();
        this.name = source.readString();
        this.isNew = source.readByte() != 0;
        this.description = source.readByte() != 0;
    }

    protected NewBrand(Parcel in) {
        this.pic = in.readString();
        this.name = in.readString();
        this.isNew = in.readByte() != 0;
        this.description = in.readByte() != 0;
    }

    public static final Creator<NewBrand> CREATOR = new Creator<NewBrand>() {
        @Override
        public NewBrand createFromParcel(Parcel source) {
            return new NewBrand(source);
        }

        @Override
        public NewBrand[] newArray(int size) {
            return new NewBrand[size];
        }
    };
}
