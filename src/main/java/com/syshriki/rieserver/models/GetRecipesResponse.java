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

    public Boolean hasNextPage(){
        return hasNextPage;
    }

    public Long nextCursor(){
        return nextCursor;
    }

    public List<RecipeDto> recipes(){
        return recipes;
    }
}
