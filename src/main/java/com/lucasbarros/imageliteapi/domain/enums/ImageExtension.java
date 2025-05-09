package com.lucasbarros.imageliteapi.domain.enums;

import java.util.Arrays;

import org.springframework.http.MediaType;

import lombok.Getter;

public enum ImageExtension {

	PNG(MediaType.IMAGE_PNG),
	JPEG(MediaType.IMAGE_JPEG),
	GIF(MediaType.IMAGE_GIF);
	
	@Getter
	private MediaType mediaType;

	private ImageExtension(MediaType mediaType) {
		this.mediaType = mediaType;
	}
	
	public static ImageExtension valueOf(MediaType mediaType) {
		return Arrays.stream(values()).filter(
				imageExtension -> 
				imageExtension.mediaType.equals(mediaType))
				.findFirst().orElse(null);
	}
	
	public static ImageExtension ofName(String name) {
		return Arrays.stream(values())
				.filter(ie -> ie.name().equalsIgnoreCase(name))
				.findFirst()
				.orElse(null);
	}

}
