package com.smi.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {

    @Expose
    private int Mode;

    @SerializedName("Mode femmes")
    private int modeFemmes;

    @SerializedName("Mode hommes")
    private int modeHommes;

    @Override
    public String toString() {
        return "Category{" +
                "Mode=" + Mode +
                ", modeFemmes=" + modeFemmes +
                ", modeHommes=" + modeHommes +
                '}';
    }

    public int getMode() {
        return Mode;
    }

    public void setMode(int mode) {
        Mode = mode;
    }

    public int getModeFemmes() {
        return modeFemmes;
    }

    public void setModeFemmes(int modeFemmes) {
        this.modeFemmes = modeFemmes;
    }

    public int getModeHommes() {
        return modeHommes;
    }

    public void setModeHommes(int modeHommes) {
        this.modeHommes = modeHommes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Mode);
        dest.writeInt(this.modeFemmes);
        dest.writeInt(this.modeHommes);
    }

    public void readFromParcel(Parcel source) {
        this.Mode = source.readInt();
        this.modeFemmes = source.readInt();
        this.modeHommes = source.readInt();
    }

    public Category() {
    }

    protected Category(Parcel in) {
        this.Mode = in.readInt();
        this.modeFemmes = in.readInt();
        this.modeHommes = in.readInt();
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
