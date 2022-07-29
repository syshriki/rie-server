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

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    } 
    public String getDescription(){
        return description;
    }
    public String getRecipe(){
        return recipe;
    }
}
