package com.myapp.graduationproject;

import android.graphics.Bitmap;

public class MarketItem {
    String title;
    String address;
//    String phone_number;
//    String rating;
    Bitmap image1;
//    Bitmap image2;

    public MarketItem(Bitmap image1, String title, String address) {
        this.image1 = image1;
//        this.image2= image2;
        this.title = title;
        this.address= address;
//        this.phone_number= phone_number;
//      this.rating=rating;


    }

    public Bitmap getImage1() {
        return image1;
    }

//    public Bitmap getImage2() {
//        return image2;
//    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }
//    public String getPhone_number() {
//        return phone_number;
//    }
//    public String getRating() {
//        return rating;
//    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    public void setPhone_number(String phone_number) {
//        this.phone_number = phone_number;
//    }

//    public void setRating(String rating) {
//        this.rating = rating;
//    }

    public void setImage1(Bitmap image1) {
        this.image1 = image1;
    }

//    public void setImage2(Bitmap image1) {
//        this.image2 = image2;
//    }

}
