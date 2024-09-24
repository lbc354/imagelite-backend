package com.lucasbarros.imageliteapi.infra.repositories;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

	// SELECT * FROM tb_image WHERE 1 = 1 AND extension = 'value' AND (name LIKE 'value' OR tags LIKE 'value')
	default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String queryValue) {

		// SELECT * FROM tb_image WHERE 1 = 1

		Specification<Image> conjunction = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
		Specification<Image> spec = Specification.where(conjunction);

		if (extension != null) {
			// AND extension = 'value'

			Specification<Image> extensionEqual = (r, q, cb) -> cb.equal(r.get("extension"), extension);
			spec = spec.and(extensionEqual);
		}

		if (StringUtils.hasText(queryValue)) {
			// AND (name LIKE 'query' OR tags LIKE 'query')

			Specification<Image> nameLike = (r, q, cb) -> cb.like(cb.upper(r.get("name")),
					"%" + queryValue.toUpperCase() + "%");

			Specification<Image> tagsLike = (r, q, cb) -> cb.like(cb.upper(r.get("tags")),
					"%" + queryValue.toUpperCase() + "%");

			Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);

			spec.and(nameOrTagsLike);
		}

		return findAll(spec);

	}

}
