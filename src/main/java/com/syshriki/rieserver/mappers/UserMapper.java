package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.syshriki.rieserver.models.User;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User>{
  
    @Override
    public User mapRow(ResultSet rs, int map) throws SQLException {
        return new User(
            rs.getString("username"),
            rs.getLong("created_at")
        );
    }
  
}
