package com.lucasbarros.imageliteapi.application.users;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucasbarros.imageliteapi.application.jwt.JwtService;
import com.lucasbarros.imageliteapi.domain.AccessToken;
import com.lucasbarros.imageliteapi.domain.entities.User;
import com.lucasbarros.imageliteapi.domain.exceptions.DuplicatedTupleException;
import com.lucasbarros.imageliteapi.domain.services.UserService;
import com.lucasbarros.imageliteapi.infra.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userrepo;
	private final PasswordEncoder pwe;
	private final JwtService jwtService;

	@Override
	public Optional<User> getByEmail(String email) {
		return userrepo.findByEmail(email);
	}

	@Override
	@Transactional
	public User save(User user) {
		var possibleUser = getByEmail(user.getEmail());
		if (possibleUser != null) {
			throw new DuplicatedTupleException("User already exists");
		}
		encodePassword(user);
		return userrepo.save(user);
	}

	private void encodePassword(User user) {
		String rawPassword = user.getPassword();
		String encodedPassword = pwe.encode(rawPassword);
		user.setPassword(encodedPassword);
	}

	@Override
	public AccessToken authenticate(String email, String password) {
		var optionalUser = getByEmail(email);

		if (optionalUser.isEmpty()) {
			return null;
		}

		// .get() converte de Optional<User> para User
		User existingUser = optionalUser.get();
		boolean matches = pwe.matches(password, existingUser.getPassword());

		if (matches) {
			return jwtService.generateToken(existingUser);
		}

		return null;
	}

}
