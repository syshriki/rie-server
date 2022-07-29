package com.syshriki.rieserver.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.syshriki.rieserver.dao.UserDao;
import com.syshriki.rieserver.models.UserCreateRequest;
import com.syshriki.rieserver.models.UserDto;
import com.syshriki.rieserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {
    
	@Autowired
	UserDao userDao;

	@Autowired
	UserService userService;

    @PostMapping("/user")
	public String createUser(@RequestBody UserCreateRequest user, @RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		
		userService.findOrThrow(username);

		var userDto = new UserDto(
			user.getUsername(),  
			System.currentTimeMillis()/1000
		);

		try{
			userDao.create(userDto);
		} catch(Exception e){
			//TODO check error for failure reason
			System.out.println(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "something is wrong with the request " + username);
		}
		
		return user.getUsername();
	}


    @GetMapping("/user/check")
	public void checkUserExists(@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		
		userService.findOrThrow(username);
	}

}
