package com.spimpalkar.pcubedemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

/*Class to get deals list*/
public class DealModelDataList implements Serializable {

    @SerializedName("id")
    int topDealId;

    @SerializedName("title")
    String topDealTitle;

    @SerializedName("image")
    String topDealImage;

    @SerializedName("share_url")
    String topDealShareUrl;

    public int getTopDealId() {
        return topDealId;
    }

    public void setTopDealId(int topDealId) {
        this.topDealId = topDealId;
    }

    public String getTopDealTitle() {
        return topDealTitle;
    }

    public void setTopDealTitle(String topDealTitle) {
        this.topDealTitle = topDealTitle;
    }

    public String getTopDealImage() {
        return topDealImage;
    }

    public void setTopDealImage(String topDealImage) {
        this.topDealImage = topDealImage;
    }

    public String getTopDealShareUrl() {
        return topDealShareUrl;
    }

    public void setTopDealShareUrl(String topDealShareUrl) {
        this.topDealShareUrl = topDealShareUrl;
    }
}
