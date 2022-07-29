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
import java.nio.file.Files;
import java.nio.file.Path;
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
				connection.createStatement().execute(s);
				System.out.println(s);
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
		ClassLoader classLoader = this.getClass().getClassLoader();
		File db = new File(classLoader.getResource(".").getFile() + "/" + env.getProperty("dbFile"));
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUrl("jdbc:sqlite:"+db.getAbsolutePath());
		initializeDb(dataSource);
		return dataSource;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RieServerApplication.class, args);
	}

}
