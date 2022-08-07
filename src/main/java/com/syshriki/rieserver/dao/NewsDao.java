package com.syshriki.rieserver.dao;

import java.util.List;

import com.syshriki.rieserver.mappers.NewsMapper;
import com.syshriki.rieserver.models.News;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class NewsDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createNews(News r) throws Exception {
        String sql = "INSERT INTO news (id,type,text,title,author,created_at,recipe_slug) VALUES (?,?,?,?,?,?,?)";
         
        int rows = jdbcTemplate.update(sql,
            r.id(),
            r.type(),
            r.text(),
            r.title(),
            r.author(),
            r.createdAt(),
            r.recipeSlug()
        );

        if (rows > 0) {
            System.out.println("news new recipe inserted");
        }
    }

    public List<News> getLatest(Long cursor, int limit){
        String sql = "SELECT * FROM news WHERE created_at < ? ORDER BY created_at DESC LIMIT ?";

        var news = jdbcTemplate.query(sql, new NewsMapper(), cursor, limit);

        return news;
    }

    public List<News> getLatest(int limit){
        String sql = "SELECT * FROM news ORDER BY created_at DESC LIMIT ?";

        var news = jdbcTemplate.query(sql, new NewsMapper(), limit);

        return news;
    }

    public boolean deleteByRecipeSlug(String slug){
        String sql = "DELETE FROM news WHERE recipe_slug = ?";

        try {
            jdbcTemplate.update(sql, slug);
            return true;
        } catch(EmptyResultDataAccessException e){
            return false;
        } 
    }
}
