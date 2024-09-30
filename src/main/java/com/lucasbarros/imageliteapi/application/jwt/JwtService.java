package com.lucasbarros.imageliteapi.application.jwt;

import org.springframework.stereotype.Service;

import com.lucasbarros.imageliteapi.domain.AccessToken;
import com.lucasbarros.imageliteapi.domain.entities.User;

@Service
public class JwtService {

	public AccessToken generateToken(User user) {
		return new AccessToken("");
	}

}
