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
		Image image = Image.builder()
				.name(name.strip())
				.tags(String.join(" ", tags).strip()) // ["tag1","tag2"] -> "tag1 tag2"
				.size(file.getSize())
				
				.extension(ImageExtension.valueOf(
						MediaType.valueOf(file.getContentType())
						))
				
				.file(file.getBytes())
				.build();
		
		// validação ruim
		if (image.getExtension() == null) {
			return null;
		}
		
		return image;
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
