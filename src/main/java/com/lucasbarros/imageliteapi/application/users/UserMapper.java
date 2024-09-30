package com.lucasbarros.imageliteapi.application.users;

import org.springframework.stereotype.Component;

import com.lucasbarros.imageliteapi.domain.entities.User;

@Component
public class UserMapper {

	public User mapToUSer(UserDTO userdto) {
		return User
				.builder()
				.username(userdto.getUsername())
				.email(userdto.getEmail())
				.password(userdto.getPassword())
				.build();
	}

}
