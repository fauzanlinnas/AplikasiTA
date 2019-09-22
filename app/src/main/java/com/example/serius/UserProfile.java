package com.example.serius;

public class UserProfile {
    public String userEmail;
    public String userName;
    public String userAddress;
    public String userTelepon;
    public String userAge;
    public String userGoldar;
    public String userPenyakit;

    public UserProfile() {
    }

    public UserProfile(String userEmail, String userName, String userAddress, String userTelepon, String userAge, String userGoldar, String userPenyakit) {

        this.userEmail = userEmail;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userTelepon = userTelepon;
        this.userAge = userAge;
        this.userGoldar = userGoldar;
        this.userPenyakit = userPenyakit;
    }

    public String getUserAge() { return userAge; }

    public void setUserAge(String userAge) { this.userAge = userAge; }

    public String getUserEmail() { return userEmail; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getUserAddress() { return userAddress; }

    public void setUserAddress(String userAddress) { this.userAddress = userAddress; }

    public String getUserGoldar() { return userGoldar; }

    public void setUserGoldar(String userGoldar) { this.userGoldar = userGoldar; }

    public String getUserPenyakit() { return userPenyakit; }

    public void setUserPenyakit(String userPenyakit) { this.userPenyakit = userPenyakit; }

    public String getUserTelepon() { return userTelepon; }

    public void setUserTelepon(String userTelepon) { this.userTelepon = userTelepon; }
}
