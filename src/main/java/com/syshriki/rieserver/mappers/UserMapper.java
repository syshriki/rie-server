package com.syshriki.rieserver.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.syshriki.rieserver.models.UserDto;

import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<UserDto>{
  
    @Override
    public UserDto mapRow(ResultSet rs, int map) throws SQLException {
        return new UserDto(
            rs.getString("username"),
            rs.getLong("created_at")
        );
    }
  
}
