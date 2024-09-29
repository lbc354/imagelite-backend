package com.lucasbarros.imageliteapi.application.users;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return userrepo.save(user);
	}

	@Override
	public AccessToken authenticate(String email, String password) {
		return null;
	}

}
