package com.syshriki.rieserver.models;

public class RecipeCreateRequest {
    String name; 
    String description; 
    String recipe;
    public RecipeCreateRequest(String name, String description, String recipe){
        this.name = name;
        this.description = description;
        this.recipe = recipe;
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
