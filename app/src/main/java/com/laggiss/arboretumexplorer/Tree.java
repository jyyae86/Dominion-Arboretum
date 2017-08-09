package com.laggiss.arboretumexplorer;

/**
 * Created by jyyae86 on 2017-05-08.
 */

public class Tree {
    private String creatorName;
    private String commonName;
    private String sciName;
    private String crownArea;
    private int changeType;
    private double lat;
    private double lng;
    private String firebaseID;

    public Tree(){

    }

    public Tree(String creatorName, String commonName, String sciName, String crownArea, int changeType, double lat, double lng, String firebaseID){
        this.creatorName = creatorName;
        this.commonName = commonName;
        this.sciName = sciName;
        this.crownArea = crownArea;
        this.changeType = changeType;
        this.lat = lat;
        this.lng = lng;
        this.firebaseID = firebaseID;
    }

    public Tree(String creatorName, String commonName, String sciName, String crownArea, int changeType, double lat, double lng){
        this.creatorName = creatorName;
        this.commonName = commonName;
        this.sciName = sciName;
        this.crownArea = crownArea;
        this.changeType = changeType;
        this.lat = lat;
        this.lng = lng;
    }

    public Tree(String creatorName, String commonName, String sciName, String crownArea, double lat, double lng){
        this.creatorName = creatorName;
        this.commonName = commonName;
        this.sciName = sciName;
        this.crownArea = crownArea;
        this.lat = lat;
        this.lng = lng;
    }

    public String getCreatorName(){
        return creatorName;
    }

    public String getCommonName(){
        return commonName;
    }

    public String getSciName(){
        return sciName;
    }

    public String getCrownArea(){
        return crownArea;
    }

    public double getLat(){
        return lat;
    }

    public double getLng(){
        return lng;
    }

    public String getFirebaseID(){
        return firebaseID;
    }

    public int getChangeType(){
        return changeType;
    }

    public void setFirebaseID(String firebaseID){
        this.firebaseID = firebaseID;
    }
}
