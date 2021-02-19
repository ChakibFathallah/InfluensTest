package com.smi.test.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Brand implements Parcelable {

    @Expose
    private boolean isIndependant;

    @Expose
    private String href;

    @Expose
    private int offerId;

    @Expose
    private String name;

    @Expose
    private Commissions commissions;

    @Expose
    private boolean premium;

    @Expose
    private String pic;

    @Expose
    private String description;

    @Expose
    @SerializedName("catégorie")
    private Category cotegory;

    @Expose
    private String displayName;

    @SerializedName("private")
    private boolean privates;

    @Expose
    private String advertiser;


    public boolean isIndependant() {
        return isIndependant;
    }

    public void setIndependant(boolean independant) {
        isIndependant = independant;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commissions getCommissions() {
        return commissions;
    }

    public void setCommissions(Commissions commissions) {
        this.commissions = commissions;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCotegory() {
        return cotegory;
    }

    public void setCotegory(Category cotegory) {
        this.cotegory = cotegory;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isPrivates() {
        return privates;
    }

    public void setPrivates(boolean privates) {
        this.privates = privates;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }


    @Override
    public String toString() {
        return "Brand{" +
                "  \n \t \t isIndependant=" + isIndependant +
                ", \n \t \t href='" + href + '\'' +
                ", \n \t \t offerId=" + offerId +
                ", \n \t \t name='" + name + '\'' +
                ", \n \t \t commissions=" + commissions +
                ", \n \t \t premium=" + premium +
                ", \n \t \t pic='" + pic + '\'' +
                ", \n \t \t description='" + description + '\'' +
                ", \n \t \t cotegory=" + cotegory +
                ", \n \t \t displayName='" + displayName + '\'' +
                ", \n \t \t privates=" + privates +
                ", \n \t \t advertiser='" + advertiser + '\'' +
                "\n} \n \n";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isIndependant ? (byte) 1 : (byte) 0);
        dest.writeString(this.href);
        dest.writeInt(this.offerId);
        dest.writeString(this.name);
        dest.writeParcelable(this.commissions, flags);
        dest.writeByte(this.premium ? (byte) 1 : (byte) 0);
        dest.writeString(this.pic);
        dest.writeString(this.description);
        dest.writeParcelable(this.cotegory, flags);
        dest.writeString(this.displayName);
        dest.writeByte(this.privates ? (byte) 1 : (byte) 0);
        dest.writeString(this.advertiser);
    }

    public void readFromParcel(Parcel source) {
        this.isIndependant = source.readByte() != 0;
        this.href = source.readString();
        this.offerId = source.readInt();
        this.name = source.readString();
        this.commissions = source.readParcelable(Commissions.class.getClassLoader());
        this.premium = source.readByte() != 0;
        this.pic = source.readString();
        this.description = source.readString();
        this.cotegory = source.readParcelable(Category.class.getClassLoader());
        this.displayName = source.readString();
        this.privates = source.readByte() != 0;
        this.advertiser = source.readString();
    }

    public Brand() {
    }

    protected Brand(Parcel in) {
        this.isIndependant = in.readByte() != 0;
        this.href = in.readString();
        this.offerId = in.readInt();
        this.name = in.readString();
        this.commissions = in.readParcelable(Commissions.class.getClassLoader());
        this.premium = in.readByte() != 0;
        this.pic = in.readString();
        this.description = in.readString();
        this.cotegory = in.readParcelable(Category.class.getClassLoader());
        this.displayName = in.readString();
        this.privates = in.readByte() != 0;
        this.advertiser = in.readString();
    }

    public static final Parcelable.Creator<Brand> CREATOR = new Parcelable.Creator<Brand>() {
        @Override
        public Brand createFromParcel(Parcel source) {
            return new Brand(source);
        }

        @Override
        public Brand[] newArray(int size) {
            return new Brand[size];
        }
    };
}
