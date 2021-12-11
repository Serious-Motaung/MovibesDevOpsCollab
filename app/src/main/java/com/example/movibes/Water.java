package com.example.movibes;

public class Water {
    String sName,sImage;

    public Water(String sName, String sImage) {
        this.sName = sName;
        this.sImage = sImage;
    }
    public Water() {

    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsImage() {
        return sImage;
    }

    public void setsImage(String sImage) {
        this.sImage = sImage;
    }
}
