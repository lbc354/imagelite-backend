package com.lucasbarros.imageliteapi.application.images;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasbarros.imageliteapi.domain.entities.Image;
import com.lucasbarros.imageliteapi.domain.services.ImageService;
import com.lucasbarros.imageliteapi.infra.repositories.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
// lombok
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
	
	private final ImageRepository imgrep;

	@Override
	@Transactional // recomendada para operação de escrita
	public Image save(Image image) {
		return imgrep.save(image);
	}

	@Override
	public Optional<Image> getById(String id) {
		return imgrep.findById(id);
	}

}
