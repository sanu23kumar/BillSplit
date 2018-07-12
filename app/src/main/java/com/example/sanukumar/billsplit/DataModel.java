package com.example.sanukumar.billsplit;

public class DataModel {

    String articleName, cost, dateTime, friends;

    DataModel() {
        articleName = "Pizza";
        cost = "100";
        dateTime = "12/06/2018 - 05:30";
        friends = "Vineet, Adarsh";
    }

    DataModel(String articleName, String cost, String dateTime, String friends) {
        this.articleName = articleName;
        this.cost = cost;
        this.dateTime = dateTime;
        this.friends = friends;
    }

    public String getArticleName() {
        return articleName;
    }

    public String getCost() {
        return cost;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getFriends() {
        return friends;
    }
}
