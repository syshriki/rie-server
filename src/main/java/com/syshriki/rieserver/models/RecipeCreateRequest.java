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
