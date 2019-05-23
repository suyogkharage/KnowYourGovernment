package com.example.knowyourgovernment;

import java.io.Serializable;

public class Official implements Serializable{

    private String officeName;
    private String Name;
    private String partyName;
    private String city;
    private String state;
    private String zip;
    private String url;
    private String photoURL;
    private String address;
    private String Phone;
    private String email;

    private String Youtube;
    private String GooglePlus;
    private String Twiter;
    private String Facebook;

    public Official(String city, String state, String zip, String officeName, String Name, String address,
                    String partyName, String phone, String url, String email, String photoURL,  String GooglePlus,
                    String Facebook, String Twiter, String Youtube) {
        this.city= city;
        this.state = state;
        this.zip = zip;
        this.officeName = officeName;
        this.Name = Name;
        this.address = address;
        this.partyName = partyName;
        this.Phone = phone;
        this.url = url;
        this.email = email;
        this.photoURL = photoURL;
        this.Youtube = Youtube;
        this.GooglePlus = GooglePlus;
        this.Twiter = Twiter;
        this.Facebook = Facebook;
    }

    public String getName() {
        return Name;
    }

    public String getOfficeName() {
        return officeName;
    }

    public String getPartyName() {
        return partyName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return Phone;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return url;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhotoURL() {return photoURL;}

    public String getYT() {
        return Youtube;
    }
    public String getGP() {
        return GooglePlus;
    }
    public String getT() {
        return Twiter;
    }
    public String getFB() {
        return Facebook;
    }

}
