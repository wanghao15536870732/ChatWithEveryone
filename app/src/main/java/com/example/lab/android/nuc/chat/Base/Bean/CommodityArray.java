package com.example.lab.android.nuc.chat.Base.Bean;

public class CommodityArray {
    private String imgUrl;
    private String itemName;
    private String phoneName;
    private String userPhotoUrl;
    private String miniImgUrl;

    public String getMiniImgUrl() {
        return miniImgUrl;
    }

    public void setMiniImgUrl(String miniImgUrl) {
        this.miniImgUrl = miniImgUrl;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }
}
