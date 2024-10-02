package com.lucasbarros.imageliteapi.config.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lucasbarros.imageliteapi.application.jwt.InvalidTokenException;
import com.lucasbarros.imageliteapi.application.jwt.JwtService;
import com.lucasbarros.imageliteapi.domain.entities.User;
import com.lucasbarros.imageliteapi.domain.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
			) throws ServletException, IOException {

		String token = getToken(request);

		if (token != null) {
			// try catch porque getEmailFromToken pode lançar exception
			try {
				String email = jwtService.getEmailFromToken(token);
				User user = userService.getByEmail(email).get();
				setUserAsAuthenticated(user);
			} catch (InvalidTokenException e) {
				log.error("TOKEN INVÁLIDO: {}", e.getMessage());
			} catch (Exception e) {
				log.error("ERRO NA VALIDAÇÃO DO TOKEN: {}", e.getMessage());
			}
		}

		filterChain.doFilter(request, response);

	}

	private String getToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		// "Bearer {token}"
		if (authHeader != null) {
			String[] authHeaderParts = authHeader.split(" ");
			if (authHeaderParts.length == 2) {
				return authHeaderParts[1];
			}
		}
		return null;
	}

	private void setUserAsAuthenticated(User user) {
		UserDetails userDetails = org.springframework.security.core.userdetails.User
				.withUsername(user.getEmail()) // o login (username) é o email
				.password(user.getPassword())
				.roles("USER")
				.build();

		UsernamePasswordAuthenticationToken authentication = new
				UsernamePasswordAuthenticationToken(
						userDetails,
						"",
						userDetails.getAuthorities());
		// userDetails.getAuthorities() == roles + permissões

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	// para que não seja feito o filtro em páginas que não precisa estar logado
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getRequestURI().contains("/v1/users");
	}

}
