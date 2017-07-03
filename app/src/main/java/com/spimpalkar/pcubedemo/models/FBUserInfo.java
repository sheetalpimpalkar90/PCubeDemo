package com.spimpalkar.pcubedemo.models;

import java.io.Serializable;


/**
 * Created by sheetal.pimpalkar on 6/30/2017.
 */

public class FBUserInfo implements Serializable{

    String username;
    String profilePicture;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
