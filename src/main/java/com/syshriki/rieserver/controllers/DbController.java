package com.syshriki.rieserver.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbController {
    @Autowired Environment env;

	@Autowired
	ResourceLoader resourceLoader;
    
    @GetMapping("/rieserver.db")
	public ResponseEntity<Resource>  findRecipesByName() throws FileNotFoundException, IOException {
		    Resource resource = resourceLoader.getResource("classpath:"+env.getProperty("dbFile"));
            return ResponseEntity.ok()
                    .contentLength(resource.contentLength())
                    .header("Content-Disposition", "attachment; filename=rieserver.db")
                    .body(resource);
	}
}
