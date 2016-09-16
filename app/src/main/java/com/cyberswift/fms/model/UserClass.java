package com.cyberswift.fms.model;

import java.io.Serializable;


public class UserClass implements Serializable {

    public String userName;


    public String password;

    public boolean isRemember= false;
    private boolean isAllDataFetched = false;

    public boolean getIsRemember() {
        return isRemember;
    }

    public void setIsRemember(boolean isRemember) {
        this.isRemember = isRemember;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public boolean getIsAllDataFetched() {
        return isAllDataFetched;
    }

    public void setIsAllDataFetched(boolean isAllDataFetched) {
        this.isAllDataFetched = isAllDataFetched;
    }

}
