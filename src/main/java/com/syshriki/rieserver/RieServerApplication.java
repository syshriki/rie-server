package com.syshriki.rieserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
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
	
	public void initializeDb(DataSource dataSource) throws SQLException, IOException{
		File file = new File(env.getProperty("dbFile")).getAbsoluteFile();
		File schema = new File("schema.sql").getAbsoluteFile();

		String schemaSql = Files.readString(Path.of(schema.getAbsolutePath()));

		if(!file.exists()){
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
		final DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(env.getProperty("driverClassName"));
		dataSource.setUrl("jdbc:sqlite:"+env.getProperty("dbFile"));
		initializeDb(dataSource);
		return dataSource;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RieServerApplication.class, args);
	}

}
