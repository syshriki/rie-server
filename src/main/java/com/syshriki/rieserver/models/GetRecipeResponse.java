package com.syshriki.rieserver.models;

public class GetRecipeResponse {
    RecipeDto recipe;
    public GetRecipeResponse(RecipeDto recipe){
        this.recipe = recipe;
    }

    public RecipeDto getRecipe(){
        return recipe;
    }

}
