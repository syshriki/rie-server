package com.syshriki.rieserver.services;

import com.syshriki.rieserver.dao.UserDao;
import com.syshriki.rieserver.models.UserDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
	UserDao userDao;

    public UserDto findOrThrow(String username) throws ResponseStatusException{
        var user = userDao.findByUsername(username);

		if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "cannot find user " + username);
        }

        return user;

    }
}
