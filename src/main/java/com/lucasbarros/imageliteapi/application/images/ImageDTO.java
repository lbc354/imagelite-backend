package com.lucasbarros.imageliteapi.application.images;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDTO {
	
	private String url;
	private String name;
	private String extension;
	private Long size;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate uploadDate;

}
