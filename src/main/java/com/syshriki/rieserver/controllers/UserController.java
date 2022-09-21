package com.syshriki.rieserver.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import com.syshriki.rieserver.dao.RecipeDao;
import com.syshriki.rieserver.dao.RecipeFavoriteDao;
import com.syshriki.rieserver.dao.UserDao;
import com.syshriki.rieserver.models.UserCreateRequest;
import com.syshriki.rieserver.models.GetRecipeFavoritesResponse;
import com.syshriki.rieserver.models.GetRecipesByUserResponse;
import com.syshriki.rieserver.models.GetUserProfileResponse;
import com.syshriki.rieserver.models.User;
import com.syshriki.rieserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class UserController {

	@Autowired
	UserService userService;
    
	@Autowired
	UserDao userDao;
	
	@Autowired
	RecipeDao recipeDao;

	@Autowired
	RecipeFavoriteDao recipeFavoriteDao;

    @PostMapping("/user")
	public String createUser(@RequestBody UserCreateRequest user, @RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		
		userService.findOrThrow(username);

		var userDto = new User(
			user.username(),  
			System.currentTimeMillis()/1000
		);

		try{
			userDao.create(userDto);
		} catch(Exception e){
			//TODO check error for failure reason
			System.out.println(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "something is wrong with the request " + username);
		}
		
		return user.username();
	}


    @GetMapping("/user/check")
	public void checkUserExists(@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		
		userService.findOrThrow(username);
	}

	@GetMapping("/user/{username}/profile")
	public GetUserProfileResponse getUserProfile(
		@PathVariable String username, 
		@RequestHeader HttpHeaders headers) {

		var user = headers.get("Authorization").get(0);
		
		var authenticatedUser =  userService.findOrThrow(user);
		return new GetUserProfileResponse(authenticatedUser);
	}

	
	@GetMapping("/user/{author}/recipes")
	public GetRecipesByUserResponse findRecipesByUserName(
		@RequestParam Long cursor, 
		@RequestParam String q, 
		@PathVariable String author, 
		@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		int limit = 20;
		userService.findOrThrow(username);

		var recipes = recipeDao.fuzzyFindTitleByUserWithFavorite(author, username, q, cursor, limit);
		var recipeCount = recipeDao.countRecipesByUsername(author, q);
		var favoriteCount = recipeFavoriteDao.countFavoritesByUsername(author, q);
		int count = recipes.size();
		Long nextCursor = count < limit ? null : recipes.get(count-1).createdAt();
		
		return new GetRecipesByUserResponse(nextCursor != null, nextCursor, recipes, recipeCount, favoriteCount);
	}

	
	@GetMapping("/user/{author}/favorites")
	public GetRecipeFavoritesResponse findFavoritesByUserName(
		@RequestParam Long cursor, 
		@RequestParam String q, 
		@PathVariable String author, 
		@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		int limit = 20;
		userService.findOrThrow(username);

		var recipes = recipeDao.fuzzyFindTitleByUserWithFavoriteOnly(author, username, q, cursor, limit);
		var recipeCount = recipeDao.countRecipesByUsername(author, q);
		var favoriteCount = recipeFavoriteDao.countFavoritesByUsername(author, q);

		int count = recipes.size();
		Long nextCursor = count < limit ? null : recipes.get(count-1).createdAt();
		
		return new GetRecipeFavoritesResponse(nextCursor != null, nextCursor, recipes, favoriteCount, recipeCount);
	}
}
