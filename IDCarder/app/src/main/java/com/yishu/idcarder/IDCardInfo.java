package com.yishu.idcarder;


/**
 * Created by Administrator on 2016/2/4.
 */
public class IDCardInfo
{
    private String name;
    private String gender;
    private String nation;
    private String birth;
    private String address;
    private String IDNumber;
    private String department;
    private String lifecycle;
    private byte[] head_image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(String lifecycle) {
        this.lifecycle = lifecycle;
    }

    public byte[] getHead_image() {
        return head_image;
    }

    public void setHead_image(byte[] head_image) {
        this.head_image = head_image;
    }
}
