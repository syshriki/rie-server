package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.syshriki.rieserver.models.RecipeDto;

import org.springframework.jdbc.core.RowMapper;

public class RecipeMapper implements RowMapper<RecipeDto>{
  
    @Override
    public RecipeDto mapRow(ResultSet rs, int map) throws SQLException {
        return new RecipeDto(
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
