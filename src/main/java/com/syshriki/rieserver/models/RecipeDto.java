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

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getRecipe(){
        return recipe;
    }
    public String getDescription(){
        return description;
    } 
    public Long getCreatedAt(){
        return createdAt;
    } 
    public String getAuthor(){
        return author;
    } 
    public String getSlug(){
        return slug;
    }
}
