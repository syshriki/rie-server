package com.syshriki.rieserver.controllers;

import com.syshriki.rieserver.dao.NewsDao;
import com.syshriki.rieserver.dao.RecipeDao;
import com.syshriki.rieserver.models.RecipeDto;
import com.syshriki.rieserver.models.RecipeUpdateRequest;
import com.syshriki.rieserver.services.NewsService;
import com.syshriki.rieserver.services.RecipeService;
import com.syshriki.rieserver.services.UserService;
import com.syshriki.rieserver.models.GetRecipeResponse;
import com.syshriki.rieserver.models.GetRecipesResponse;
import com.syshriki.rieserver.models.RecipeCreateRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class RecipeController {

	@Autowired
	RecipeDao recipeDao;

	@Autowired
	NewsDao newsDao;

	@Autowired
	RecipeService recipeService;

	@Autowired
	NewsService newsService;

	@Autowired
	UserService userService;

	@GetMapping("/recipes")
	public GetRecipesResponse findRecipesByName(
		@RequestParam Long cursor, 
		@RequestParam String q, 
		@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		int limit = 20;
		userService.findOrThrow(username);

		var recipes = recipeDao.fuzzyFindByName(q, cursor, limit);
		
		int count = recipes.size();
		Long nextCursor = count < limit ? null : recipes.get(count-1).createdAt();
		
		return new GetRecipesResponse(nextCursor != null, nextCursor, recipes);
	}

	@GetMapping("/recipes/{slug}")
	public GetRecipeResponse getBySlug(
		@PathVariable String slug,
		@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		
		userService.findOrThrow(username);

		RecipeDto recipe = recipeDao.findBySlug(slug);

		if(recipe == null){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipe with slug " + slug + " not found");
		}

		return new GetRecipeResponse(recipe);
	}

    @PostMapping("/recipes")
	@Transactional
	public String createRecipe(@RequestBody RecipeCreateRequest recipe, @RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		
		userService.findOrThrow(username);

		var recipeId = recipeService.generateRecipeId(username);
		
		var recipeSlug = recipeService.generateRecipeId(username);

		var recipeDto = new RecipeDto(
			recipeId, 
			recipe.name(), 
			recipe.recipe(), 
			recipe.description(), 
			System.currentTimeMillis()/1000, 
			username,
			recipeSlug
		);

		try{
			recipeDao.create(recipeDto);
			newsService.newRecipe(recipeDto);
		} catch(Exception e){
			//TODO check error for failure reason
			System.out.println(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "something is wrong with the request " + username);
		}
		
		return recipeDto.slug();
	}

	@PutMapping("/recipes")
	@Transactional
	public String updateRecipe(
		@RequestBody RecipeUpdateRequest recipe, 
		@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		
		userService.findOrThrow(username);

		var originalRecipe = recipeDao.findById(recipe.id());
		
		if(originalRecipe == null){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipe with id " + recipe.id() + " not found");
		}

		var newRecipeId = recipeService.generateRecipeId(username);

		var recipeDto = new RecipeDto(
			newRecipeId, 
			recipe.name(), 
			recipe.recipe(), 
			recipe.description(), 
			System.currentTimeMillis()/1000, 
			originalRecipe.author(),
			originalRecipe.slug()
		);

		if(recipeDao.deleteBySlug(originalRecipe.slug())){
			recipeDao.create(recipeDto);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cannot delete recipe with id " + originalRecipe.id());	
		}
			
		return originalRecipe.slug();
	}

	@DeleteMapping("/recipes/{slug}")
	@Transactional
	public String deleteRecipe(
		@RequestHeader HttpHeaders headers,
		@PathVariable String slug) {
		var username = headers.get("Authorization").get(0);

		userService.findOrThrow(username);

		var originalRecipe = recipeDao.findBySlug(slug);

		if(originalRecipe == null){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "recipe with slug " + slug + " not found");
		}

		if(!originalRecipe.author().equals(username)){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, username + " is not owner of recipe");
        }

		try{
			recipeDao.deleteBySlug(slug);
			newsDao.deleteByRecipeSlug(slug);
		} catch(Exception e){
			//TODO check error for failure reason
			System.out.println(e);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "something is wrong with the request " + username);
		}
		
		return slug;
	}
}