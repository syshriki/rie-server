package com.syshriki.rieserver.models;

public record RecipeDto(String id, String name, String recipe, String description, Long createdAt, String author, String slug) {
    
}
