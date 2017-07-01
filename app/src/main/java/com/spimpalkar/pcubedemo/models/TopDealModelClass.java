package com.spimpalkar.pcubedemo.models;

import java.io.Serializable;

/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class TopDealModelClass implements Serializable{

    int topDealId;
    String topDealTitle;
    String topDealImage;
    String topDealDescription;

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

    public String getTopDealDescription() {
        return topDealDescription;
    }

    public void setTopDealDescription(String topDealDescription) {
        this.topDealDescription = topDealDescription;
    }

}
