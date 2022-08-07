package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.syshriki.rieserver.models.RecipeWithFavorite;

public class RecipeWithFavoriteMapper implements RowMapper<RecipeWithFavorite>{
  
    @Override
    public RecipeWithFavorite mapRow(ResultSet rs, int map) throws SQLException {
        return new RecipeWithFavorite(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("recipe"),
                rs.getString("description"),
                rs.getLong("created_at"),
                rs.getString("author"),
                rs.getString("slug"),
                rs.getBoolean("isFavorite")
        );
    }
  
}