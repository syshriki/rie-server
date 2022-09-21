package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.syshriki.rieserver.models.RecipeTitleWithFavorite;

public class RecipeTitleWithFavoriteMapper implements RowMapper<RecipeTitleWithFavorite>{
  
    @Override
    public RecipeTitleWithFavorite mapRow(ResultSet rs, int map) throws SQLException {
        return new RecipeTitleWithFavorite(
                rs.getString("id"),
                rs.getString("name"),
                rs.getLong("created_at"),
                rs.getString("author"),
                rs.getString("slug"),
                rs.getBoolean("isFavorite")
        );
    }
  
}