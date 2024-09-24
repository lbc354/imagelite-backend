package com.lucasbarros.imageliteapi.infra.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

public class GenericSpecs {
	
	private GenericSpecs() {}
	
	// T significa que é qualquer parâmetro que quero receber
	public static <T> Specification<T> conjunction() {
		return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
	}

}
