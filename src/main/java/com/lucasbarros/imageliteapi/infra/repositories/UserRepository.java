package com.lucasbarros.imageliteapi.infra.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucasbarros.imageliteapi.domain.entities.User;

public interface UserRepository extends JpaRepository<User, String> {

}
