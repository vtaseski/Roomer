package com.roomer.models;

import java.util.Date;

/**
 * Created by vladotaseski on 7/17/17.
 */

public class Apartment {
    public int Id;

    public String Title;

    public int Rooms;

    public String GPS;

    public String Category;

    public int SqMeters;

    public boolean Bathroom;

    public boolean Kitchen;

    public boolean Furnished;

    public String Phone;

    public boolean Bedroom;

    public String Description;

    public int Price;

    public int Floor;

    public String Location;

    public int Views;

    public String Created;

    public String Currency;

    public String MainPicture;

    public Apartment(int id, String title, int rooms, String GPS, int sqMeters, boolean bathroom,
                     boolean kitchen, boolean furnished, String phone, boolean bedroom, String description,
                     int price, int floor, String location, int views, String created,
                     String currency, String mainPicture, String category
                    ) {
        Id = id;
        Title = title;
        Rooms = rooms;
        this.GPS = GPS;
        SqMeters = sqMeters;
        Bathroom = bathroom;
        Kitchen = kitchen;
        Furnished = furnished;
        Phone = phone;
        Bedroom = bedroom;
        Description = description;
        Price = price;
        Floor = floor;
        Location = location;
        Views = views;
        Created = created;
        Currency = currency;
        MainPicture = mainPicture;
        Category = category;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategory() { return Category; }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getRooms() {
        return Rooms;
    }

    public void setRooms(int rooms) {
        Rooms = rooms;
    }

    public String getGPS() {
        return GPS;
    }

    public void setGPS(String GPS) {
        this.GPS = GPS;
    }

    public int getSqMeters() {
        return SqMeters;
    }

    public void setSqMeters(int sqMeters) {
        SqMeters = sqMeters;
    }

    public boolean isBathroom() {
        return Bathroom;
    }

    public void setBathroom(boolean bathroom) {
        Bathroom = bathroom;
    }

    public boolean isKitchen() {
        return Kitchen;
    }

    public void setKitchen(boolean kitchen) {
        Kitchen = kitchen;
    }

    public boolean isFurnished() {
        return Furnished;
    }

    public void setFurnished(boolean furnished) {
        Furnished = furnished;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public boolean isBedroom() {
        return Bedroom;
    }

    public void setBedroom(boolean bedroom) {
        Bedroom = bedroom;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getFloor() {
        return Floor;
    }

    public void setFloor(int floor) {
        Floor = floor;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getViews() {
        return Views;
    }

    public void setViews(int views) {
        Views = views;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getMainPicture() {
        return MainPicture;
    }

    public void setMainPicture(String mainPicture) {
        MainPicture = mainPicture;
    }
}
