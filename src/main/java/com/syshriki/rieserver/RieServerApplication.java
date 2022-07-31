package com.syshriki.rieserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.sql.DataSource;


@SpringBootApplication
public class RieServerApplication {
	
	@Autowired Environment env;
	
	@Autowired
	ResourceLoader resourceLoader;

	public void initializeDb(DataSource dataSource) throws SQLException, IOException{
		Resource resource = resourceLoader.getResource("classpath:"+env.getProperty("dbFile"));
		
		if (!resource.exists()) {
			File schema = new File("schema.sql").getAbsoluteFile();
			String schemaSql = Files.readString(Path.of(schema.getAbsolutePath()));
			System.out.println("database does not exist!");
			var statments = schemaSql.split(";");
			var connection = dataSource.getConnection();
			for(var s: statments){
				System.out.println(s);
				connection.createStatement().execute(s);
			}
		  }
		
	}
	
	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
					.allowedOrigins("*")
					.allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS");
            }
        };
    }
	
	@Bean
	public DataSource dataSource() throws SQLException, IOException {
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		System.out.println(1);
		Resource resource = resourceLoader.getResource("classpath:"+env.getProperty("dbFile"));
		System.out.println(2);
		System.out.println(3);
		Path destination = Paths.get("./"+env.getProperty("dbFile"));
		System.out.println(4);
		try{
			Files.copy(resource.getInputStream(), destination);
			System.out.println(5);
		}catch(FileAlreadyExistsException e){
			System.out.println("db alread exists");
		}

		if(resource.exists()){
			System.out.println(destination);
			dataSource.setUrl("jdbc:sqlite:"+destination);
		}
		initializeDb(dataSource);
		return dataSource;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RieServerApplication.class, args);
	}

}
