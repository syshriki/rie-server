package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.syshriki.rieserver.models.News;

import org.springframework.jdbc.core.RowMapper;

public class NewsMapper implements RowMapper<News>{
  
    @Override
    public News mapRow(ResultSet rs, int map) throws SQLException {
        return new News(
            rs.getString("id"),
            rs.getString("type"),
            rs.getString("text"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getLong("created_at"),
            rs.getString("recipe_slug")
        );
    }
  
}
