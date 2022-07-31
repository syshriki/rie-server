package com.syshriki.rieserver.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
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
    		Path destination = Paths.get("./"+env.getProperty("dbFile"));
            File f = destination.toFile();
            InputStream inputStream = new FileInputStream(f);
            InputStreamResource inputStreamResource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .contentLength(f.length())
                    .header("Content-Disposition", "attachment; filename=rieserver.db")
                    .body(inputStreamResource);
	}
}
