package com.lucasbarros.imageliteapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // para @CreatedDate na entidade Image
public class ImageliteapiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}
