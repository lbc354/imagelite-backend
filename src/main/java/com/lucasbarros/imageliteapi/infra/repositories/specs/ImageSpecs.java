package com.lucasbarros.imageliteapi.infra.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;

public class ImageSpecs {
	
	private ImageSpecs() {}
	
	public static Specification<Image> extensionEqual(ImageExtension ext) {
		return (r, q, cb) -> cb.equal(r.get("extension"), ext);
	}
	
	public static Specification<Image> nameLike(String name) {
		return (r, q, cb) -> cb.like(cb.upper(r.get("name")), "%" + name.toUpperCase() + "%");
	}
	
	public static Specification<Image> tagsLike(String tags) {
		return (r, q, cb) -> cb.like(cb.upper(r.get("tags")), "%" + tags.toUpperCase() + "%");
	}

}
