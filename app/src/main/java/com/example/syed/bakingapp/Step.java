package com.example.syed.bakingapp;

/**
 * Created by syed on 2017-08-10.
 */

public class Step {
    private int ID;
    private String shortDescription;
    private String description;
    private String videoUrl;
    private String imgURL;


    public Step(int ID, String shortDescription) {
        this.ID = ID;
        this.shortDescription = shortDescription;
    }

    public int getID() {
        return ID;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
