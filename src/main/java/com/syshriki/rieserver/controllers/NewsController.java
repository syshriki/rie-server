package com.syshriki.rieserver.controllers;

import com.syshriki.rieserver.dao.NewsDao;
import com.syshriki.rieserver.models.GetNewsResponse;
import com.syshriki.rieserver.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {
    @Autowired
    NewsDao newsDao;

	@Autowired
	UserService userService;

    @GetMapping("/news")
	public GetNewsResponse findRecipesByName(
		@RequestParam(required=false) Long cursor,
		@RequestHeader HttpHeaders headers) {
		var username = headers.get("Authorization").get(0);
		int limit = 20;
		userService.findOrThrow(username);

		var news = cursor == null ? 
			newsDao.getLatest(limit) : 
			newsDao.getLatest(cursor, limit) ;
		
		int count = news.size();
		Long nextCursor =  count < limit ? null : news.get(count -1).getCreatedAt();
		
		return new GetNewsResponse(nextCursor != null, nextCursor, news);
	}
}
