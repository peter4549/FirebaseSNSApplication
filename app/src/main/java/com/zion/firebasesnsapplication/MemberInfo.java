package com.zion.firebasesnsapplication;

public class MemberInfo {
    private String name;
    private String phone;
    private String birthday;
    private String address;

    MemberInfo(String name, String phone, String birthday, String address) {
        this.name = name;
        this.phone = name;
        this.birthday = birthday;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
