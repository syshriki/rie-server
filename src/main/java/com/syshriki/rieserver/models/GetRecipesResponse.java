package com.syshriki.rieserver.models;

import java.util.List;

public record GetRecipesResponse(Boolean hasNextPage, Long nextCursor, List<RecipeDto> recipes) {
    
}
