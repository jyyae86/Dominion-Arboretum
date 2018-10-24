package com.laggiss.arboretumexplorer;

/**
 * Created by jyyae86 on 2017-05-08.
 */

public class Tree {
//    private String creatorName;
//    private String commonName;
//    private String sciName;
//    private String crownArea;
//    private int changeType;
//    private double lat;
//    private double lng;
//    private String firebaseID;

    private String family, familiarName, genus, species, rank, type,
            hybridCross, cultivar, authority, dateIntro, accessNo, recdFrom, dateRecd, howRecd, numRecd, nameRecd,
            commonName, nomCommun, nursery, location,
            collSeed, sourceAcc, revised, numberNow, origins, herbSpec,
            idByDate, photo1, photo2, mortInfo, notes, memo, firebaseID;

    private boolean donor, nameStatus;
    private int changeType;


    private double lat, lng;

    public Tree(){}

    public Tree(String family, String familiarName, String genus, String species, String rank,
                String type, String hybridCross, String cultivar, boolean nameStatus, String authority,
                String dateIntro, String accessNo, String recdFrom, String dateRecd, String howRecd,
                String numRecd, String nameRecd, String commonName, String nomCommun, String nursery,
                String location, boolean donor, String collSeed, String sourceAcc, String revised,
                String numberNow, String origins, String herbSpec, String idByDate, String photo1,
                String photo2, String mortInfo, String notes, String memo, double lat, double lng,
                String firebaseID, int changeType){
        this.family = family;
        this.familiarName = familiarName;
        this.genus = genus;
        this.species = species;
        this.rank = rank;
        this.type = type;
        this.hybridCross = hybridCross;
        this.cultivar = cultivar;
        this.nameStatus = nameStatus;
        this.authority = authority;
        this.dateIntro = dateIntro;
        this.accessNo = accessNo;
        this.recdFrom = recdFrom;
        this.dateRecd = dateRecd;
        this.howRecd = howRecd;
        this.numRecd = numRecd;
        this.nameRecd = nameRecd;
        this.commonName = commonName;
        this.nomCommun = nomCommun;
        this.nursery = nursery;
        this.location = location;
        this.donor = donor;
        this.collSeed = collSeed;
        this.sourceAcc = sourceAcc;
        this.revised = revised;
        this.numberNow = numberNow;
        this.origins = origins;
        this.herbSpec = herbSpec;
        this.idByDate = idByDate;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.mortInfo = mortInfo;
        this.notes = notes;
        this.memo = memo;
        this.lat = lat;
        this.lng = lng;
        this.changeType = changeType;
        this.firebaseID = firebaseID;
    }

    public Tree(String family, String familiarName, String genus, String species, String rank,
                String type, String hybridCross, String cultivar, boolean nameStatus, String authority,
                String dateIntro, String accessNo, String recdFrom, String dateRecd, String howRecd,
                String numRecd, String nameRecd, String commonName, String nomCommun, String nursery,
                String location, boolean donor, String collSeed, String sourceAcc, String revised,
                String numberNow, String origins, String herbSpec, String idByDate, String photo1,
                String photo2, String mortInfo, String notes, String memo, double lat, double lng, int changeType){
        this.family = family;
        this.familiarName = familiarName;
        this.genus = genus;
        this.species = species;
        this.rank = rank;
        this.type = type;
        this.hybridCross = hybridCross;
        this.cultivar = cultivar;
        this.nameStatus = nameStatus;
        this.authority = authority;
        this.dateIntro = dateIntro;
        this.accessNo = accessNo;
        this.recdFrom = recdFrom;
        this.dateRecd = dateRecd;
        this.howRecd = howRecd;
        this.numRecd = numRecd;
        this.nameRecd = nameRecd;
        this.commonName = commonName;
        this.nomCommun = nomCommun;
        this.nursery = nursery;
        this.location = location;
        this.donor = donor;
        this.collSeed = collSeed;
        this.sourceAcc = sourceAcc;
        this.revised = revised;
        this.numberNow = numberNow;
        this.origins = origins;
        this.herbSpec = herbSpec;
        this.idByDate = idByDate;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.mortInfo = mortInfo;
        this.notes = notes;
        this.memo = memo;
        this.lat = lat;
        this.lng = lng;
        this.changeType = changeType;
    }

    public Tree(String family, String familiarName, String genus, String species, String rank,
                String type, String hybridCross, String cultivar, boolean nameStatus, String authority,
                String dateIntro, String accessNo, String recdFrom, String dateRecd, String howRecd,
                String numRecd, String nameRecd, String commonName, String nomCommun, String nursery,
                String location, boolean donor, String collSeed, String sourceAcc, String revised,
                String numberNow, String origins, String herbSpec, String idByDate, String photo1,
                String photo2, String mortInfo, String notes, String memo, double lat, double lng){
        this.family = family;
        this.familiarName = familiarName;
        this.genus = genus;
        this.species = species;
        this.rank = rank;
        this.type = type;
        this.hybridCross = hybridCross;
        this.cultivar = cultivar;
        this.nameStatus = nameStatus;
        this.authority = authority;
        this.dateIntro = dateIntro;
        this.accessNo = accessNo;
        this.recdFrom = recdFrom;
        this.dateRecd = dateRecd;
        this.howRecd = howRecd;
        this.numRecd = numRecd;
        this.nameRecd = nameRecd;
        this.commonName = commonName;
        this.nomCommun = nomCommun;
        this.nursery = nursery;
        this.location = location;
        this.donor = donor;
        this.collSeed = collSeed;
        this.sourceAcc = sourceAcc;
        this.revised = revised;
        this.numberNow = numberNow;
        this.origins = origins;
        this.herbSpec = herbSpec;
        this.idByDate = idByDate;
        this.photo1 = photo1;
        this.photo2 = photo2;
        this.mortInfo = mortInfo;
        this.notes = notes;
        this.memo = memo;
        this.lat = lat;
        this.lng = lng;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getFamiliarName() {
        return familiarName;
    }

    public void setFamiliarName(String familiarName) {
        this.familiarName = familiarName;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHybridCross() {
        return hybridCross;
    }

    public void setHybridCross(String hybridCross) {
        this.hybridCross = hybridCross;
    }

    public String getCultivar() {
        return cultivar;
    }

    public void setCultivar(String cultivar) {
        this.cultivar = cultivar;
    }

    public boolean getNameStatus() {
        return nameStatus;
    }

    public String getStringNameStatus(){
        return String.valueOf(nameStatus);
    }

    public void setNameStatus(boolean nameStatus) {
        this.nameStatus = nameStatus;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getDateIntro() {
        return dateIntro;
    }

    public void setDateIntro(String dateIntro) {
        this.dateIntro = dateIntro;
    }

    public String getAccessNo() {
        return accessNo;
    }

    public void setAccessNo(String accessNo) {
        this.accessNo = accessNo;
    }

    public String getRecdFrom() {
        return recdFrom;
    }

    public void setRecdFrom(String recdFrom) {
        this.recdFrom = recdFrom;
    }

    public String getDateRecd() {
        return dateRecd;
    }

    public void setDateRecd(String dateRecd) {
        this.dateRecd = dateRecd;
    }

    public String getHowRecd() {
        return howRecd;
    }

    public void setHowRecd(String howRecd) {
        this.howRecd = howRecd;
    }

    public String getNumRecd() {
        return numRecd;
    }

    public void setNumRecd(String numRecd) {
        this.numRecd = numRecd;
    }

    public String getNameRecd() {
        return nameRecd;
    }

    public void setNameRecd(String nameRecd) {
        this.nameRecd = nameRecd;
    }

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(String commonName) {
        this.commonName = commonName;
    }

    public String getNomCommun() {
        return nomCommun;
    }

    public void setNomCommun(String nomCommun) {
        this.nomCommun = nomCommun;
    }

    public String getNursery() {
        return nursery;
    }

    public void setNursery(String nursery) {
        this.nursery = nursery;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public boolean getDonor() {
        return donor;
    }

    public String getStringDonor(){
        return String.valueOf(donor);
    }

    public void setDonor(boolean donor) {
        this.donor = donor;
    }

    public String getCollSeed() {
        return collSeed;
    }

    public void setCollSeed(String collSeed) {
        this.collSeed = collSeed;
    }

    public String getSourceAcc() {
        return sourceAcc;
    }

    public void setSourceAcc(String sourceAcc) {
        this.sourceAcc = sourceAcc;
    }

    public String getRevised() {
        return revised;
    }

    public void setRevised(String revised) {
        this.revised = revised;
    }

    public String getNumberNow() {
        return numberNow;
    }

    public void setNumberNow(String numberNow) {
        this.numberNow = numberNow;
    }

    public String getOrigins() {
        return origins;
    }

    public void setOrigins(String origins) {
        this.origins = origins;
    }

    public String getHerbSpec() {
        return herbSpec;
    }

    public void setHerbSpec(String herbSpec) {
        this.herbSpec = herbSpec;
    }

    public String getIdByDate() {
        return idByDate;
    }

    public void setIdByDate(String idByDate) {
        this.idByDate = idByDate;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getMortInfo() {
        return mortInfo;
    }

    public void setMortInfo(String mortInfo) {
        this.mortInfo = mortInfo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFirebaseID() {
        return firebaseID;
    }

    public void setFirebaseID(String firebaseID) {
        this.firebaseID = firebaseID;
    }

    public int getChangeType() {
        return changeType;
    }

    public void setChangeType(int changeType) {
        this.changeType = changeType;
    }

//    public Tree(String creatorName, String commonName, String sciName, String crownArea, int changeType, double lat, double lng, String firebaseID){
//        this.creatorName = creatorName;
//        this.commonName = commonName;
//        this.sciName = sciName;
//        this.crownArea = crownArea;
//        this.changeType = changeType;
//        this.lat = lat;
//        this.lng = lng;
//        this.firebaseID = firebaseID;
//    }
//
//    public Tree(String creatorName, String commonName, String sciName, String crownArea, int changeType, double lat, double lng){
//        this.creatorName = creatorName;
//        this.commonName = commonName;
//        this.sciName = sciName;
//        this.crownArea = crownArea;
//        this.changeType = changeType;
//        this.lat = lat;
//        this.lng = lng;
//    }
//
//    public Tree(String creatorName, String commonName, String sciName, String crownArea, double lat, double lng){
//        this.creatorName = creatorName;
//        this.commonName = commonName;
//        this.sciName = sciName;
//        this.crownArea = crownArea;
//        this.lat = lat;
//        this.lng = lng;
//    }

}
