package com.smi.test.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Purchase {

    @Expose
    private int affiliateId;

    @Expose
    private boolean isPrivate;

    @Expose
    private String currency;

    @Expose
    private String commissionAffiliate;

    @Expose
    private int offerId;

    @Expose
    private String afClickId;

    @Expose
    private String countryCode;

    @Expose
    private String influencer;

    @Expose
    private String goal;

    @Expose
    private String brandName;

    @Expose
    private int amount;

    @Expose
    private int createdAt;

    public int getAffiliateId() {
        return affiliateId;
    }

    public void setAffiliateId(int affiliateId) {
        this.affiliateId = affiliateId;
    }

    public boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCommissionAffiliate() {
        return commissionAffiliate;
    }

    public void setCommissionAffiliate(String commissionAffiliate) {
        this.commissionAffiliate = commissionAffiliate;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getAfClickId() {
        return afClickId;
    }

    public void setAfClickId(String afClickId) {
        this.afClickId = afClickId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getInfluencer() {
        return influencer;
    }

    public void setInfluencer(String influencer) {
        this.influencer = influencer;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "affiliateId=" + affiliateId +
                ", isPrivate=" + isPrivate +
                ", currency='" + currency + '\'' +
                ", commissionAffiliate='" + commissionAffiliate + '\'' +
                ", offerId=" + offerId +
                ", afClickId='" + afClickId + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", influencer='" + influencer + '\'' +
                ", goal='" + goal + '\'' +
                ", brandName='" + brandName + '\'' +
                ", amount=" + amount +
                ", createdAt=" + createdAt +
                '}';
    }
}
