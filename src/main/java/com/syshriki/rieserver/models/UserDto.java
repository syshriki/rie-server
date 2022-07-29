package com.syshriki.rieserver.models;

public class UserDto{
    String username;
    Long createdAt;
    public UserDto(String username, Long createdAt) {
        this.username = username;
        this.createdAt = createdAt;
    }
    
    public String getUsername(){
        return username;
    }

    public Long getCreatedAt(){
        return createdAt;
    }
}
