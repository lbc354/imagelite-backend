package com.lucasbarros.imageliteapi.application.users;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "http://localhost:3000")
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
			// no spring boot temos o jackson que faz essa conversão para json,
			// tanto converte de json para objeto como também, quando retornamos uma
			// resposta,
			// converte a responsta em json, e uma forma de criar um json sem precisar
			// criar um objeto é fazendo map, onde a chave é o nome da propriedade ("error")
			// e o valor é o valor do json (e.getMessage())

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
