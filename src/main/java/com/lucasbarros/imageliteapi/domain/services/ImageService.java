package com.lucasbarros.imageliteapi.domain.services;

import java.util.Optional;

import com.lucasbarros.imageliteapi.domain.entities.Image;

public interface ImageService {
	
	Image save(Image image);
	
	// usamos optional para caso o id não exista
	Optional<Image> getById(String id);

}
