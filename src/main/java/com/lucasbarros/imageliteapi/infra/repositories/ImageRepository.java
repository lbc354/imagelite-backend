package com.lucasbarros.imageliteapi.infra.repositories;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {
	
	// SELECT * FROM tb_image WHERE 1 = 1 AND extension = 'value' AND (name LIKE 'query' OR tags LIKE 'query')
	default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String queryValue) {
		
		// SELECT * FROM tb_image WHERE 1 = 1
		// 1 = 1 é uma conjunction que dá sempre true (retorna todas as imagens)
		Specification<Image> conjunction = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
		// root é os dados da entidade (no caso, Image)
		// query é a query que está sendo montada (SELECT * FROM tb_image)
		// criteriaBuilder é o objeto em que é construído os critérios (WHERE 1 = 1 ...)
		// query é utilizada dentro de um specification para fazer join (query.join(), etc.)
		// root é utilizado para acessar algum campo da entidade para comparar com outro
		Specification<Image> spec = Specification.where(conjunction);
		
		if (extension != null) {
			// AND extension = 'value'
			
			Specification<Image> extensionEqual = (r, q, cb) -> cb.equal(
					r.get("extension"),
					extension);
			spec = spec.and(extensionEqual);
		}
		
		// não fazemos a verificação if (queryValue != null) porque pode acabar vindo apenas espaço " "
		if (StringUtils.hasText(queryValue)) {
			// AND (name LIKE 'query' OR tags LIKE 'query')
			
			Specification<Image> nameLike = (r, q, cb) -> cb.like(
					cb.upper(r.get("name")),
					"%" + queryValue.toUpperCase() + "%");
			// transformamos ambos valores (do banco e da pesquisa) em caixa alta para compararmos
			Specification<Image> tagsLike = (r, q, cb) -> cb.like(
					cb.upper(r.get("tags")),
					"%" + queryValue.toUpperCase() + "%");
			
			Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);
			
			spec.and(nameOrTagsLike);
		}
		
		return findAll(spec);
		
	}

}
