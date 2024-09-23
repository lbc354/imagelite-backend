package com.lucasbarros.imageliteapi.application.images;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.enums.ImageExtension;
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
public class ImagesController {
	
	private final ImageService imgserv;
	// o spring vai pegar uma implementação dessa interface (em ImageServiceImpl), vai instanciar um objeto e vai injetar aqui para utilizarmos
	
	// formato utilizado por formulários com mídia: multipart/form-data
	// @RequestParam(value = "file", required = false) MultipartFile file -> parâmetro opcional
	
	@PostMapping
	public ResponseEntity save(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("tags") List<String> tags
			) throws IOException {

		log.info("Imagem recebida. name: {}, size: {}", file.getOriginalFilename(), file.getSize());
		
		Image image = Image.builder()
				.name(name)
				.tags(String.join(";", tags)) // ["tag1","tag2"] -> "tag1,tag2"
				.size(file.getSize())
				
				.extension(ImageExtension.valorDe(
						MediaType.valueOf(file.getContentType())
						))
				
				.file(file.getBytes())
				.build();
		
		imgserv.save(image);
		
		return ResponseEntity.ok().build();

	}

}
