package com.syshriki.rieserver.models;

public class NewsDto{
    String id; 
    String type;
    String text;
    String title;
    String author;
    Long createdAt; 
    String recipeSlug;

     public NewsDto(String id, String type, String text, String title, String author, Long createdAt, String recipeSlug){
        this.id = id;
        this.type = type;
        this.text = text;
        this.title = title;
        this.author = author;
        this.createdAt = createdAt;
        this.recipeSlug = recipeSlug;
     }

    public String id(){
        return id;
    }
    public String type(){
        return type;
    }
    public String text(){
        return text;
    }
    public String title(){
        return title;
    }
    public String author(){
        return author;
    }
    public Long createdAt(){
        return createdAt;
    } 
    public String recipeSlug(){
        return recipeSlug;
    }
    }