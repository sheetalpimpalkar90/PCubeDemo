package com.spimpalkar.pcubedemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class DealModel implements Serializable {

    @SerializedName("deals")
    DealDataModel dealDataModel;

    public DealDataModel getDealDataModel() {
        return dealDataModel;
    }

    public void setDealDataModel(DealDataModel dealDataModel) {
        this.dealDataModel = dealDataModel;
    }

    /*Class to get deal object*/
    public class DealDataModel implements Serializable {

        @SerializedName("total_count")
        int totalDealCount;

        @SerializedName("data")
        ArrayList<DealModelDataList> modelDataListArrayList;

        public int getTotalDealCount() {
            return totalDealCount;
        }

        public void setTotalDealCount(int totalDealCount) {
            this.totalDealCount = totalDealCount;
        }

        public ArrayList<DealModelDataList> getModelDataListArrayList() {
            return modelDataListArrayList;
        }

        public void setModelDataListArrayList(ArrayList<DealModelDataList> modelDataListArrayList) {
            this.modelDataListArrayList = modelDataListArrayList;
        }

//        /*Class to get deals list*/
//       public class DealModelDataList implements Serializable {
//
//            @SerializedName("id")
//            int topDealId;
//
//            @SerializedName("title")
//            String topDealTitle;
//
//            @SerializedName("image")
//            String topDealImage;
//
//            @SerializedName("share_url")
//            String topDealShareUrl;
//
//            public int getTopDealId() {
//                return topDealId;
//            }
//
//            public void setTopDealId(int topDealId) {
//                this.topDealId = topDealId;
//            }
//
//            public String getTopDealTitle() {
//                return topDealTitle;
//            }
//
//            public void setTopDealTitle(String topDealTitle) {
//                this.topDealTitle = topDealTitle;
//            }
//
//            public String getTopDealImage() {
//                return topDealImage;
//            }
//
//            public void setTopDealImage(String topDealImage) {
//                this.topDealImage = topDealImage;
//            }
//
//            public String getTopDealShareUrl() {
//                return topDealShareUrl;
//            }
//
//            public void setTopDealShareUrl(String topDealShareUrl) {
//                this.topDealShareUrl = topDealShareUrl;
//            }
//        }
    }

}
