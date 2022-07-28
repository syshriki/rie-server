package com.syshriki.rieserver.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DbController {
    @Autowired Environment env;

    @GetMapping("/rieserver.db")
	public ResponseEntity<InputStreamResource>  findRecipesByName() throws FileNotFoundException {
            File file = new File(env.getProperty("dbFile")).getAbsoluteFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentLength(file.length())
                    .header("Content-Disposition", "attachment; filename=rieserver.db")
                    .body(resource);
	}
}
