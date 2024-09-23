package com.lucasbarros.imageliteapi.application.images;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

// lombok
@Data
@Builder
public class ImageDTO {
	
	private String url;
	private String name;
	private String extension;
	private Long size;
	private LocalDate uploadDate;

}
