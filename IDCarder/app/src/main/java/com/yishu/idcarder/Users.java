package com.yishu.idcarder;

/**
 * Created by Administrator on 2016/1/22.
 */
public class Users
{
    private String username;
    private String password;
    private String phone_number;
    private double money;
    private String tag; // 0 for enterprise users, 1 for personal users, 2 for enterprise added member users
    private String affiliate;
    private String enterprise_name;

    public Users(){}
    public Users(String tag, String enterprise_name, String affiliate)
    {
        this.tag = tag;
        this.enterprise_name = enterprise_name;
        this.affiliate =affiliate;
    }
    public Users(String username, String password, String phone_number, double money, String tag, String enterprise_name, String affiliate)
    {
        this.username = username;
        this.password = password;
        this.phone_number = phone_number;
        this.money = money;
        this.tag = tag;
        this.enterprise_name = enterprise_name;
        this.affiliate =affiliate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAffiliate() {
        return affiliate;
    }

    public void setAffiliate(String affiliate) {
        this.affiliate = affiliate;
    }

    public String getEnterprise_name() {
        return enterprise_name;
    }

    public void setEnterprise_name(String enterprise_name) {
        this.enterprise_name = enterprise_name;
    }
}
