package com.syshriki.rieserver.models;

public record NewsDto (String id, String type, String text, String title, String author, Long createdAt, String recipeSlug){}