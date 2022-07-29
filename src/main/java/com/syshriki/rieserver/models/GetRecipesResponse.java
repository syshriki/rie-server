package com.syshriki.rieserver.models;

import java.util.List;

public class GetRecipesResponse {
    Boolean hasNextPage; 
    Long nextCursor; 
    List<RecipeDto> recipes;

    public GetRecipesResponse(Boolean hasNextPage, Long nextCursor, List<RecipeDto> recipes){
        this.hasNextPage = hasNextPage;
        this.nextCursor = nextCursor;
        this.recipes = recipes;
    }    

    public Boolean getHasNextPage(){
        return hasNextPage;
    }

    public Long getNextCursor(){
        return nextCursor;
    }

    public List<RecipeDto> getRecipes(){
        return recipes;
    }
}
