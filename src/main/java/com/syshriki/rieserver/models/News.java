package com.syshriki.rieserver.models;

public record News (String id, String type, String text, String title, String author, Long createdAt, String recipeSlug){}