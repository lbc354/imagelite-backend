package com.lucasbarros.imageliteapi.domain.services;

import java.util.List;
import java.util.Optional;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;

public interface ImageService {
	
	Image save(Image image);
	
	// optional para caso o id não exista
	Optional<Image> getById(String id);
	
	List<Image> search(ImageExtension extension, String query);

}
