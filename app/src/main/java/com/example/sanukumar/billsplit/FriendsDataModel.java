package com.example.sanukumar.billsplit;

public class FriendsDataModel {

    String friendName, phone, email, nickName;

    FriendsDataModel() {
        friendName = "Yudhvir";
        phone = "9596984288";
        email = "yudhvir@gmail.com";
        nickName = "yuddu";
    }

    FriendsDataModel(String friendName, String phone, String email, String nickName) {
        this.friendName = friendName;
        this.phone = phone;
        this.email = email;
        this.nickName = nickName;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getNickName() {
        return nickName;
    }
}
