package com.lucasbarros.imageliteapi.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasbarros.imageliteapi.domain.entities.Image;

public interface ImageRepository extends JpaRepository<Image, String> {

}
