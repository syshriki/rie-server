package com.syshriki.rieserver.services;

import com.syshriki.rieserver.dao.NewsDao;
import com.syshriki.rieserver.models.NewsDto;
import com.syshriki.rieserver.models.RecipeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsService {
    @Autowired
	NewsDao newsDao;

    private String generateId(String newsType, String relatedId){
        return newsType + "_" + relatedId;
    }

    //String id, String type, String text, String title, String author, Timestamp createdAt, String recipeId
    public void newRecipe(RecipeDto recipe) throws Exception{
        var type = "recipe";
        var title = "New recipe added!";
        var text = recipe.name();
        var newsId = generateId(type, recipe.id());
        Long createdAt = System.currentTimeMillis()/1000;
        var newsDto = new NewsDto(newsId, type, text, title, recipe.author(), createdAt, recipe.slug());
        newsDao.createNews(newsDto);
    }
}
