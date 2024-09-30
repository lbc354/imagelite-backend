package com.lucasbarros.imageliteapi.application.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.lucasbarros.imageliteapi.domain.AccessToken;
import com.lucasbarros.imageliteapi.domain.entities.User;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	private final SecretKeyGenerator secretKeyGenerator;

	public AccessToken generateToken(User user) {
		String token = Jwts
				.builder()
				.signWith(secretKeyGenerator.getKey())
				.subject(user.getEmail()) // identificador único
				.expiration(expirationDate())
				.claims(tokenClaims(user))
				.compact();

		return new AccessToken(token);
	}
	
	private Date expirationDate() {
		var expirationMinutes = 60;
		LocalDateTime deadline = LocalDateTime.now().plusMinutes(expirationMinutes);
		return Date.from(deadline.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	// informações para o front-end como nome, etc. ("olá fulano")
	private Map<String, Object> tokenClaims(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", user.getUsername());
		return claims;
	}

}
