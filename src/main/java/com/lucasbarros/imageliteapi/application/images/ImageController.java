package com.lucasbarros.imageliteapi.application.images;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.services.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// todo controlador rest precisa dessa annotation para que ele receba requisições http
@RestController
// para dizer qual url ele vai ficar escutando
@RequestMapping("/v1/images")
// lombok
@Slf4j // para os logs
@RequiredArgsConstructor
public class ImageController {
	
	private final ImageService imgserv;
	private final ImageMapper mapper;
	
	@PostMapping
	public ResponseEntity save(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("tags") List<String> tags
			) throws IOException {

		log.info("Imagem recebida. name: {}, size: {}",
				file.getOriginalFilename(), file.getSize());
		
		Image image = mapper.mapToImage(file, name, tags);
		Image savedImage = imgserv.save(image);
		URI imgUri = buildImageURL(savedImage);
		
		return ResponseEntity.created(imgUri).build();

	}
	
	// http://localhost:8080/v1/images/image-id
	private URI buildImageURL(Image image) {
		String imagePath = "/" + image.getId();
		return ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(imagePath)
				.build()
				.toUri();
	}
	
	
	
	@GetMapping("{uuid}")
	public ResponseEntity<byte[]> getImage(@PathVariable("uuid") String id) {
		var possibleImage = imgserv.getById(id);
		
		if (possibleImage.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		// o get retorna o objeto que está dentro do optional
		var image = possibleImage.get();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(image.getExtension().getMediaType());
		headers.setContentLength(image.getSize());
		// \" é a forma de escrever aspas duplas
		headers.setContentDispositionFormData(
				"inline; filename=\"" + image.getFileName() + "\"",
				image.getFileName());
		
		return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
	}

}
