package com.lucasbarros.imageliteapi.infra.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
	
	default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {
		if (extension != null) {
			// add in query
		}
		return findAll();
	}

}
