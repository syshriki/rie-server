package com.syshriki.rieserver.dao;

import com.syshriki.rieserver.mappers.UserMapper;
import com.syshriki.rieserver.models.User;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(User userDto) throws Exception {
        String sql = "INSERT INTO users (username,created_at) VALUES (?,?)";
         
        int rows = jdbcTemplate.update(sql,
        userDto.username(),
            userDto.createdAt()
        );

        if (rows > 0) {
            System.out.println("user inserted");
        }
    }

    public User findByUsername(String username){
        String sql = "SELECT username,created_at from users where username=? limit 1";

        try{
            User user = jdbcTemplate.queryForObject(sql, new UserMapper(), username);
            return user;
        } catch(EmptyResultDataAccessException e){
            return null;
        } 
    }

    public List<User> findByUsername(String username, Long cursor, int limit){
        String sql = "SELECT username,created_at from users where username LIKE ? and created_at < ? ORDER BY created_at DESC LIMIT ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), "%"+username+"%", cursor, limit);
        return users;
    }

    public List<User> countByUsername(String username, Long cursor, int limit){
        String sql = "SELECT username,created_at from users where username LIKE ? and created_at < ? ORDER BY created_at DESC LIMIT ?";
        List<User> users = jdbcTemplate.query(sql, new UserMapper(), "%"+username+"%", cursor, limit);
        return users;
    }
}
