package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.syshriki.rieserver.models.RecipeFavorite;

public class RecipeFavoriteMapper  implements RowMapper<RecipeFavorite>{
  
    @Override
    public RecipeFavorite mapRow(ResultSet rs, int map) throws SQLException {
        return new RecipeFavorite(
            rs.getString("username"),
            rs.getString("recipe_slug"),
            rs.getLong("created_at")
        );
    }
  
}

