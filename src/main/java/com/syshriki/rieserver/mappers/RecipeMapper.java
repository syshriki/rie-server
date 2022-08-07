package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.syshriki.rieserver.models.Recipe;

import org.springframework.jdbc.core.RowMapper;

public class RecipeMapper implements RowMapper<Recipe>{
  
    @Override
    public Recipe mapRow(ResultSet rs, int map) throws SQLException {
        return new Recipe(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("recipe"),
            rs.getString("description"),
            rs.getLong("created_at"),
            rs.getString("author"),
            rs.getString("slug")
        );
    }
  
}
