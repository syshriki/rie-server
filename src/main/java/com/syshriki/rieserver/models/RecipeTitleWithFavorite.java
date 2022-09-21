package com.syshriki.rieserver.models;

public record RecipeTitleWithFavorite(String id, String name, Long createdAt, String author, String slug,  Boolean isFavorite) {
    
}