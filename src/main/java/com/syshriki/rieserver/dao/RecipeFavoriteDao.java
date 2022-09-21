package com.syshriki.rieserver.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.syshriki.rieserver.mappers.RecipeFavoriteMapper;
import com.syshriki.rieserver.models.RecipeFavorite;

@Repository
public class RecipeFavoriteDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public boolean delete(String username, String slug) {
        String sql = "DELETE FROM recipe_favorites WHERE recipe_slug = ? AND username = ?";
        jdbcTemplate.update(sql, slug, username);
        System.out.println("recipe " + slug + " unfavorited by " + username);
        return false;
    }

    public boolean create(String username, String slug) {
        String sql = "INSERT INTO recipe_favorites (username, recipe_slug, created_at) VALUES (?,?,?)";
        jdbcTemplate.update(sql, username, slug, System.currentTimeMillis() / 1000);
        System.out.println("recipe " + slug + " favorited by " + username);
        return true;
    }

    public RecipeFavorite find(String username, String slug) {
        String sql = "SELECT * FROM recipe_favorites WHERE username = ? AND  recipe_slug = ? LIMIT 1;";
        try{
            var recipeFavorite = jdbcTemplate.queryForObject(sql, new RecipeFavoriteMapper(), username, slug);
            return recipeFavorite;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
    
    public Integer countFavoritesByUsername(String username, String name){
        String sql = "SELECT count(1) as count FROM recipe_favorites rf INNER JOIN recipes r on r.slug = rf.recipe_slug WHERE r.deleted_at is NULL AND rf.username = ?  AND r.name LIKE ?;";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, "%"+name+"%");        
        return count;
    }
    
    public RecipeFavorite findByUser(String username, String cursor) {
        String sql = "SELECT r.title, r.author, r.created_at FROM recipe_favorites INNER JOIN recipes r on r.id = rf.recipeId WHERE username = ? AND created_at > ? LIMIT 20;";
        try{
            var recipeFavorite = jdbcTemplate.queryForObject(sql, new RecipeFavoriteMapper(), username, cursor);
            return recipeFavorite;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }
}
