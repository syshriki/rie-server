package com.syshriki.rieserver.models;

import java.util.List;

public record GetRecipesByUserResponse(Boolean hasNextPage, Long nextCursor, List<RecipeTitleWithFavorite> recipes, Integer recipeCount, Integer favoriteCount) {
    
}

