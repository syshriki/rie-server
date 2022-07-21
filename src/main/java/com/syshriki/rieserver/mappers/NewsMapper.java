package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.syshriki.rieserver.models.NewsDto;

import org.springframework.jdbc.core.RowMapper;

public class NewsMapper implements RowMapper<NewsDto>{
  
    @Override
    public NewsDto mapRow(ResultSet rs, int map) throws SQLException {
        return new NewsDto(
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
