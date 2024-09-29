package com.lucasbarros.imageliteapi.domain.exceptions;

public class DuplicatedTupleException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicatedTupleException(String message) {
		super(message);
	}

}
