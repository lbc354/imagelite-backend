package com.lucasbarros.imageliteapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;
import com.lucasbarros.imageliteapi.infra.repository.ImageRepository;

@SpringBootApplication
@EnableJpaAuditing // para @CreatedDate na entidade Image
public class ImageliteapiApplication {
	
	@Bean // registra dentro do contexto do spring
	// @Autowired serve para injetar um objeto registrado no contexto do spring
	public CommandLineRunner cmdlnrnr(@Autowired ImageRepository imgRep) {
		return args -> {
			Image img = Image
					.builder()
					.extension(ImageExtension.PNG)
					.name("myImage")
					.tags("myTag")
					.size(1000L)
					.build();
			imgRep.save(img);
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(ImageliteapiApplication.class, args);
	}

}
