package com.example.serius;

import java.util.List;

public class UserProfile {
    public String userEmail;
    public String userName;
    public String userAddress;
    public String userTelepon;
    public String userAge;
    public String userGoldar;
    public String userPenyakit;
    public String userToken;
    public List<String> dataDonor;
    public double userWillDonor;

    public UserProfile() {
    }

    public UserProfile(String _userEmail, String _userName, String _userAddress, String _userTelepon,
                       String _userAge, String _userGoldar, String _userPenyakit, String _userToken,
                       List<String> _dataDonor, double _userWillDonor) {

        this.userEmail = _userEmail;
        this.userName = _userName;
        this.userAddress = _userAddress;
        this.userTelepon = _userTelepon;
        this.userAge = _userAge;
        this.userGoldar = _userGoldar;
        this.userPenyakit = _userPenyakit;
        this.userToken = _userToken;
        this.dataDonor = _dataDonor;
        this.userWillDonor = _userWillDonor;
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

    public List<String> getDataDonor() {
        return dataDonor;
    }

    public void setDataDonor(List<String> dataDonor) {
        this.dataDonor = dataDonor;
    }

    public double getUserWillDonor() {
        return userWillDonor;
    }

    public String getUserToken() {
        return userToken;
    }
}
