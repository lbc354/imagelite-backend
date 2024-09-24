package com.lucasbarros.imageliteapi.infra.repositories;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;
import com.lucasbarros.imageliteapi.infra.repositories.specs.GenericSpecs;
import com.lucasbarros.imageliteapi.infra.repositories.specs.ImageSpecs;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

	// SELECT * FROM tb_image WHERE 1 = 1
	// AND extension = 'value'
	// AND (name LIKE 'query' OR tags LIKE 'query')
	default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query) {

		// SELECT * FROM tb_image WHERE 1 = 1
		Specification<Image> spec = Specification.where(GenericSpecs.conjunction());

		if (extension != null) {
			// AND extension = 'value'
			spec = spec.and(ImageSpecs.extensionEqual(extension));
		}

		if (StringUtils.hasText(query)) {
			// AND (name LIKE 'query' OR tags LIKE 'query')
			spec = spec.and(Specification.anyOf(
					ImageSpecs.nameLike(query),
					ImageSpecs.tagsLike(query)));
		}

		return findAll(spec);

	}

}
