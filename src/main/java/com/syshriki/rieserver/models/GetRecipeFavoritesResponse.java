package com.syshriki.rieserver.models;

import java.util.List;

public record GetRecipeFavoritesResponse(Boolean hasNextPage, Long nextCursor, List<RecipeTitleWithFavorite> recipes, Integer favoriteCount, Integer recipeCount) {
    
}

