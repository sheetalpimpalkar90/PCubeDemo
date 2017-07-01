package com.spimpalkar.pcubedemo.models;

import java.io.Serializable;

/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class PopularDealModel implements Serializable{

    int popularDealId;
    String popularDealTitle;
    String popularDealImage;
    String popularDealDescription;

    public int getPopularDealId() {
        return popularDealId;
    }

    public void setPopularDealId(int popularDealId) {
        this.popularDealId = popularDealId;
    }

    public String getPopularDealTitle() {
        return popularDealTitle;
    }

    public void setPopularDealTitle(String popularDealTitle) {
        this.popularDealTitle = popularDealTitle;
    }

    public String getPopularDealImage() {
        return popularDealImage;
    }

    public void setPopularDealImage(String popularDealImage) {
        this.popularDealImage = popularDealImage;
    }

    public String getPopularDealDescription() {
        return popularDealDescription;
    }

    public void setPopularDealDescription(String popularDealDescription) {
        this.popularDealDescription = popularDealDescription;
    }

}
