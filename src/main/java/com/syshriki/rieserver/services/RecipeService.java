package com.syshriki.rieserver.services;

import com.syshriki.rieserver.dao.RecipeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    @Autowired
	RecipeDao recipeDao;

    public String generateRecipeId(String author){
        var userRecipeCount = recipeDao.countRecipesByAuthor(author);
		var recipeId = author + "-" + Integer.toString(userRecipeCount);
        return recipeId;
    }

    
    public String generateRecipeSlug(String author){
        var userRecipeCount = recipeDao.countRecipesByAuthor(author);
		var recipeSlug = "rcp" + "-" + author + "-" + Integer.toString(userRecipeCount);
        return recipeSlug;
    }
}
