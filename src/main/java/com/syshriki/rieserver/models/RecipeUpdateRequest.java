package com.syshriki.rieserver.models;

public class RecipeUpdateRequest {
    String id;
    String name; 
    String description;
    String recipe;
    public RecipeUpdateRequest(String id, String name, String description, String recipe){
        this.id = id;
        this.name = name;
        this.description = description;
        this.recipe = recipe;
    }

    public String id(){
        return id;
    }
    public String name(){
        return name;
    } 
    public String description(){
        return description;
    }
    public String recipe(){
        return recipe;
    }
}
