package com.lucasbarros.imageliteapi.application.images;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;

@Component
public class ImageMapper {
	
	public Image mapToImage(MultipartFile file, String name, List<String> tags) throws IOException {
		return Image.builder()
				.name(name)
				.tags(String.join(";", tags)) // ["tag1","tag2"] -> "tag1,tag2"
				.size(file.getSize())
				
				.extension(ImageExtension.valorDe(
						MediaType.valueOf(file.getContentType())
						))
				
				.file(file.getBytes())
				.build();
	}
	
	public ImageDTO imageToDTO(Image image, String url) {
		return ImageDTO.builder()
				.url(url)
				.extension(image.getExtension().name())
				.name(image.getName())
				.size(image.getSize())
				.uploadDate(image.getUploadDate().toLocalDate())
				.build();
	}

}
