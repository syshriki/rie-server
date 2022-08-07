package com.syshriki.rieserver.models;

public record Recipe(String id, String name, String recipe, String description, Long createdAt, String author, String slug) {
    
}
