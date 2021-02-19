package com.smi.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Commissions implements Parcelable {

    @SerializedName("commission générale")
    private String commissionGen;

    public String getCommissionGen() {
        return commissionGen;
    }

    public void setCommissionGen(String commissionGen) {
        this.commissionGen = commissionGen;
    }

    @Override
    public String toString() {
        return "Commissions{" +
                "commissionGen='" + commissionGen + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.commissionGen);
    }

    public void readFromParcel(Parcel source) {
        this.commissionGen = source.readString();
    }

    public Commissions() {
    }

    protected Commissions(Parcel in) {
        this.commissionGen = in.readString();
    }

    public static final Parcelable.Creator<Commissions> CREATOR = new Parcelable.Creator<Commissions>() {
        @Override
        public Commissions createFromParcel(Parcel source) {
            return new Commissions(source);
        }

        @Override
        public Commissions[] newArray(int size) {
            return new Commissions[size];
        }
    };
}
