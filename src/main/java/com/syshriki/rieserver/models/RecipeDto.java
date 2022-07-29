package com.syshriki.rieserver.models;

public class RecipeDto{
    String id;
    String name;
    String recipe;
    String description; 
    Long createdAt; 
    String author; 
    String slug;
    public RecipeDto(String id, String name, String recipe, String description, Long createdAt, String author, String slug) {
        this.id = id;
        this.name = name;
        this.recipe = recipe;
        this.description = description;
        this.createdAt = createdAt;
        this.author = author;
        this.slug = slug;
    } 

    public String id(){
        return id;
    }
    public String name(){
        return name;
    }
    public String recipe(){
        return recipe;
    }
    public String description(){
        return description;
    } 
    public Long createdAt(){
        return createdAt;
    } 
    public String author(){
        return author;
    } 
    public String slug(){
        return slug;
    }
}
