package com.lucasbarros.imageliteapi.application.images;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

// todo controlador rest precisa dessa annotation para que ele receba requisições http
@RestController
// para dizer qual url ele vai ficar escutando
@RequestMapping("/v1/images")
// para os logs
@Slf4j
public class ImagesController {
	
	// método que vai receber o upload de uma imagem
	// formato utilizado por formulários com mídia: multipart/form-data
	// @RequestBody: É usada para mapear o corpo da requisição HTTP para um objeto em Java. É normalmente utilizada para lidar com dados em formato JSON, XML ou outros tipos de conteúdo enviado no corpo da requisição.
	// @RequestParam: É usada para extrair parâmetros da URL ou da query string (parte da URL após o ?). Esses parâmetros geralmente estão no formato de dados de formulário ou são valores simples, como números, strings, etc.
	// Portanto, a anotação @RequestParam não é limitada a qualquer formato específico, mas é usada para capturar parâmetros de URL, enquanto @RequestBody lida com o corpo da requisição, tipicamente em JSON ou outro formato similar.
	// @RequestParam(value = "file", required = false) MultipartFile file -> parâmetro opcional
	
	@PostMapping
	public ResponseEntity save(
			@RequestParam("file") MultipartFile file,
			@RequestParam("name") String name,
			@RequestParam("tags") List<String> tags
			) {

		log.info("Imagem recebida. name: {}, size: {}", file.getOriginalFilename(), file.getSize());
		log.info("Nome dado para a imagem: {}", name);
		log.info("Tags: {}", tags);
		return ResponseEntity.ok().build();

	}

}
