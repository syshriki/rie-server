package com.syshriki.rieserver.models;

public class UserCreateRequest {
    String username;
    public UserCreateRequest(String username){
        this.username = username;
    }
    
    public String getUsername(){
        return username;
    }

}
