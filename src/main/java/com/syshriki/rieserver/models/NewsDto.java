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

    public String getId(){
        return id;
    }
    public String getType(){
        return type;
    }
    public String getText(){
        return text;
    }
    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public Long getCreatedAt(){
        return createdAt;
    } 
    public String getRecipeSlug(){
        return recipeSlug;
    }
    }