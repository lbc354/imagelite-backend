package com.lucasbarros.imageliteapi.application.users;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucasbarros.imageliteapi.domain.entities.User;
import com.lucasbarros.imageliteapi.domain.exceptions.DuplicatedTupleException;
import com.lucasbarros.imageliteapi.domain.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserMapper userMapper;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody UserDTO userdto) {

		try {

			User user = userMapper.mapToUSer(userdto);
			userService.save(user);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} catch (DuplicatedTupleException e) {

			Map<String, String> jsonResult = Map.of("error", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(jsonResult);

		}

	}

	@PostMapping("/auth")
	public ResponseEntity<?> auhtenticate(@RequestBody CredentialsDTO credentials) {

		var token = userService.authenticate(credentials.getEmail(), credentials.getPassword());

		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		return ResponseEntity.ok(token);

	}

}
