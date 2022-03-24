package com.moviesdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@Controller
public class MoviesDbApplication {

	@RequestMapping("/")
	public String index() {
		return "redirect:/movies/";
	}

	public static void main(String[] args) {
		SpringApplication.run(MoviesDbApplication.class, args);
	}

}
