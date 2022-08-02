package com.syshriki.rieserver.dao;

import java.util.List;

import com.syshriki.rieserver.mappers.RecipeMapper;
import com.syshriki.rieserver.models.RecipeDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String create(RecipeDto r)  {
        String sql = "INSERT INTO recipes (id,name,recipe,description,created_at,author,slug) VALUES (?,?,?,?,?,?,?)";
         
        int rows = jdbcTemplate.update(sql,
            r.id(),
            r.name(),
            r.recipe(),
            r.description(),
            r.createdAt(),
            r.author(),
            r.slug()
        );

        if (rows > 0) {
            System.out.println("recipe "+r.name()+" inserted");
        }

        return r.id();
    }

    public RecipeDto findById(String id){
        String sql = "SELECT * FROM recipes WHERE id = ? LIMIT 1";
        try {
            var recipe = jdbcTemplate.queryForObject(sql, new RecipeMapper(), id);
            return recipe;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public RecipeDto findBySlug(String slug){
        String sql = "SELECT * FROM recipes WHERE slug = ? AND deleted_at is NULL ORDER BY created_at DESC LIMIT 1";
        try {
            var recipe = jdbcTemplate.queryForObject(sql, new RecipeMapper(), slug);
            return recipe;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<RecipeDto> fuzzyFindByName(String name, Long cursor, int limit){
        String sql = "SELECT id, name, slug, description, author, created_at, recipe FROM recipes WHERE created_at < ? AND name LIKE ? AND deleted_at is NULL ORDER BY created_at DESC LIMIT ?";
        var recipes = jdbcTemplate.query(sql, new RecipeMapper(), cursor, "%"+name+"%", limit);

        return recipes;
    }

    public boolean deleteBySlug(String slug) {
        String sql = "UPDATE recipes set deleted_at = ? WHERE slug = ? AND deleted_at is NULL";
        try {
            jdbcTemplate.update(sql, System.currentTimeMillis()/1000, slug);
            return true;
        } catch(EmptyResultDataAccessException e){
            return false;
        } 
    }

    public Integer countRecipesByAuthor(String username){
        String sql = "SELECT count(1) from recipes where author=?";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        
        return count;
    }
}
