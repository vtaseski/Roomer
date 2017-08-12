package com.roomer.models;

/**
 * Created by antoniotodorov on 8/12/17.
 */

public class Roommate {

    public int Id;
    public String UserId;
    public String Title;
    public String Facebook;
    public String Phone;
    public String Desctiption;
    public String Created;
    public int PriceFrom;
    public int PriceTo;
    public int M2From;
    public int M2To;
    public boolean SeparateRoom;
    public boolean FixedPrice;
    public String FirstName;
    public String SecondName;
    public String Picture;


    public Roommate(int id, String userId, String title, String facebook, String phone, String desctiption,
                    String created, int priceFrom, int priceTo, int sqMetersFrom, int sqMetersTo,
                    boolean separateRoom, boolean fixedPrice, String firstName, String secondName, String picture) {
        Id = id;
        UserId = userId;
        Title = title;
        Facebook = facebook;
        Phone = phone;
        Desctiption = desctiption;
        Created = created;
        PriceFrom = priceFrom;
        PriceTo = priceTo;
        M2From = sqMetersFrom;
        M2To = sqMetersTo;
        SeparateRoom = separateRoom;
        FixedPrice = fixedPrice;
        FirstName = firstName;
        SecondName = secondName;
        Picture=picture;



    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getFacebook() {
        return Facebook;
    }

    public void setFacebook(String facebook) {
        Facebook = facebook;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getDesctiption() {
        return Desctiption;
    }

    public void setDesctiption(String desctiption) {
        Desctiption = desctiption;
    }

    public String getCreated() {
        return Created;
    }

    public void setCreated(String created) {
        Created = created;
    }

    public int getPriceFrom() {
        return PriceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        PriceFrom = priceFrom;
    }

    public int getPriceTo() {
        return PriceTo;
    }

    public void setPriceTo(int priceTo) {
        PriceTo = priceTo;
    }

    public int getM2From() {
        return M2From;
    }

    public void setM2From(int m2From) {
        M2From = m2From;
    }

    public int getM2To() {
        return M2To;
    }

    public void setM2To(int m2To) {
        M2To = m2To;
    }

    public boolean isSeparateRoom() {
        return SeparateRoom;
    }

    public void setSeparateRoom(boolean separateRoom) {
        SeparateRoom = separateRoom;
    }

    public boolean isFixedPrice() {
        return FixedPrice;
    }

    public void setFixedPrice(boolean fixedPrice) {
        FixedPrice = fixedPrice;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }
    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }
}
