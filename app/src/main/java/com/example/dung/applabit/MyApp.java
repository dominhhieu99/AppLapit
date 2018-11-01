package com.example.dung.applabit;

import android.app.Application;

import com.example.dung.applabit.Model.User;

public class MyApp extends Application {
    private User user;
    private String imgAvatarUrl;
    private double latitude;
    private double longitude;

    private static MyApp insatnce;


    @Override
    public void onCreate() {
        super.onCreate();
        insatnce = this;
    }

    public static MyApp getInsatnce() {
        if (insatnce == null) {
            insatnce = new MyApp();
        }
        return insatnce;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getImgAvatarUrl() {
        return imgAvatarUrl;
    }

    public void setImgAvatarUrl(String imgAvatarUrl) {
        this.imgAvatarUrl = imgAvatarUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
