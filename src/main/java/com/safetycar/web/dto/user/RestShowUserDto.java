package com.safetycar.web.dto.user;

public class RestShowUserDto {
    private int userId;
    private String fullName;
    private String username;
    private String address;
    private String telephone;


    public RestShowUserDto() {

    }


    public int getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getAddress() {
        return address;
    }

    public String getTelephone() {
        return telephone;
    }



    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


}
