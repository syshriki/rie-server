package com.syshriki.rieserver.models;

import java.util.List;

public record GetRecipesByUserResponse(Boolean hasNextPage, String nextCursor, List<RecipeTitleWithFavorite> recipes, Integer recipeCount, Integer favoriteCount) {
    
}

