package com.lucasbarros.imageliteapi.application.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lucasbarros.imageliteapi.domain.AccessToken;
import com.lucasbarros.imageliteapi.domain.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
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

	public String getEmailFromToken(String tokenJwt) {
		try {
		
			JwtParser build = Jwts.parser().verifyWith(secretKeyGenerator.getKey()).build();
		
			Jws<Claims> jwsClaims = build.parseSignedClaims(tokenJwt);
			// no momento do parse (decodificar token),
			// se o token estiver expirado ele vai lançar uma exception (JwtException),
			// ou se o token não for um jwt, se for uma string qualquer,
			// ele também lança exception
			
			Claims claims = jwsClaims.getPayload();
			
			return claims.getSubject();
		
		} catch (JwtException e) {
			throw new InvalidTokenException(e.getMessage());
		}
	}

}
