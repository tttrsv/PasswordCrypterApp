package com.company.domain;

public class Password {
    private String password = "";
    private int length = 0;

    public void setPassword(String password){
        this.password = password;
        this.length = password.length();
    }

    public String getPassword(){
        return this.password;
    }

    public int getLength(){
        return this.length;
    }

}
