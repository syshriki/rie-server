package com.syshriki.rieserver.dao;

import com.syshriki.rieserver.mappers.UserMapper;
import com.syshriki.rieserver.models.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(UserDto userDto) throws Exception {
        String sql = "INSERT INTO users (username,created_at) VALUES (?,?)";
         
        int rows = jdbcTemplate.update(sql,
        userDto.getUsername(),
            userDto.getCreatedAt()
        );

        if (rows > 0) {
            System.out.println("recipe inserted");
        }
    }

    public UserDto findByUsername(String username){
        String sql = "SELECT username,created_at from users where username=? limit 1";

        try{
            UserDto user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
            return user;
        } catch(EmptyResultDataAccessException e){
            return null;
        } 
    }
}
