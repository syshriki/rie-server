package com.syshriki.rieserver.models;

public record RecipeWithFavorite(String id, String name, String recipe, String description, Long createdAt, String author, String slug, Boolean isFavorite) {
    
}
