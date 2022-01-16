package com.example.gymmanagementuser.model;

public class UserInfo {
    private String userFullName, userEmailAddress, userAddress, userBirthDate, userGender, userPhoneNumber, userImageLink;

    public UserInfo() {
    }

    public UserInfo(String userFullName, String userEmailAddress, String userAddress, String userBirthDate, String userGender, String userPhoneNumber, String userImageLink) {
        this.userFullName = userFullName;
        this.userEmailAddress = userEmailAddress;
        this.userAddress = userAddress;
        this.userBirthDate = userBirthDate;
        this.userGender = userGender;
        this.userPhoneNumber = userPhoneNumber;
        this.userImageLink = userImageLink;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(String userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserBirthDate() {
        return userBirthDate;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserImageLink() {
        return userImageLink;
    }

    public void setUserImageLink(String userImageLink) {
        this.userImageLink = userImageLink;
    }
}
