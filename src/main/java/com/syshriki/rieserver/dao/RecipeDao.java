package com.syshriki.rieserver.dao;

import java.util.List;

import com.syshriki.rieserver.mappers.RecipeMapper;
import com.syshriki.rieserver.mappers.RecipeTitleWithFavoriteMapper;
import com.syshriki.rieserver.mappers.RecipeWithFavoriteMapper;
import com.syshriki.rieserver.models.Recipe;
import com.syshriki.rieserver.models.RecipeTitleWithFavorite;
import com.syshriki.rieserver.models.RecipeWithFavorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RecipeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String create(Recipe r)  {
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

    public Recipe findById(String id){
        String sql = "SELECT * FROM recipes WHERE id = ? LIMIT 1";
        try {
            var recipe = jdbcTemplate.queryForObject(sql, new RecipeMapper(), id);
            return recipe;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }


    public RecipeWithFavorite findBySlugWithFavorite(String username, String slug){
        String sql = "SELECT r.*, rf.created_at is NOT NULL isFavorite FROM recipes r LEFT JOIN recipe_favorites rf on r.slug = rf.recipe_slug AND rf.username = ? WHERE r.slug = ? AND r.deleted_at is NULL ORDER BY r.created_at DESC LIMIT 1;";
        try {
            var recipe = jdbcTemplate.queryForObject(sql, new RecipeWithFavoriteMapper(), username, slug);
            return recipe;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<RecipeTitleWithFavorite> fuzzyFindTitleByUserWithFavorite(String recipeAuthor, String username, String name, Long cursor, int limit){
        String sql = "SELECT r.id, r.name, r.author, r.slug, r.created_at, rf.created_at is NOT NULL isFavorite FROM recipes r LEFT JOIN recipe_favorites rf on r.slug = rf.recipe_slug AND rf.username = ? WHERE r.deleted_at is NULL AND r.created_at < ? AND r.name LIKE ? AND r.author = ? ORDER BY r.created_at DESC LIMIT ?;";
        var recipes = jdbcTemplate.query(sql, new RecipeTitleWithFavoriteMapper(), username, cursor, "%"+name+"%", recipeAuthor, limit);
        return recipes;
    
    }

    public List<RecipeTitleWithFavorite> fuzzyFindTitleByUserWithFavoriteOnly(String username, String viewer, String name, Long cursor, int limit){
        String sql = "SELECT r.id, r.name, r.author, r.slug, r.created_at, rf2.created_at is NOT NULL as isFavorite FROM recipe_favorites rf INNER JOIN recipes r on r.slug = rf.recipe_slug LEFT JOIN recipe_favorites rf2 on r.slug = rf2.recipe_slug AND rf2.username = ? WHERE r.deleted_at is NULL AND rf.username = ? AND r.created_at < ? AND r.name LIKE ? ORDER BY r.created_at DESC  LIMIT ?;";
        var recipes = jdbcTemplate.query(sql, new RecipeTitleWithFavoriteMapper(), viewer, username, cursor, "%"+name+"%", limit);
        return recipes;
    }
    
    public Integer countRecipesByUsername(String username, String name){
        String sql = "SELECT count(id) as count FROM recipes WHERE author = ? AND name LIKE ? AND deleted_at is NULL;";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, "%"+name+"%");        
        return count;
    }

    public Recipe findBySlug(String slug){
        String sql = "SELECT * FROM recipes WHERE slug = ? AND deleted_at is NULL ORDER BY created_at DESC LIMIT 1;";
        try {
            var recipe = jdbcTemplate.queryForObject(sql, new RecipeMapper(), slug);
            return recipe;
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<RecipeWithFavorite> fuzzyFindByName(String username, String name, Long cursor, int limit){
        String sql = "SELECT r.*, rf.created_at is NOT NULL isFavorite FROM recipes r LEFT JOIN recipe_favorites rf on r.slug = rf.recipe_slug AND rf.username = ? WHERE r.created_at < ? AND r.name LIKE ? AND r.deleted_at is NULL ORDER BY r.created_at DESC LIMIT ?";
        var recipes = jdbcTemplate.query(sql, new RecipeWithFavoriteMapper(), username, cursor, "%"+name+"%", limit);

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
