package com.lucasbarros.imageliteapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.lucasbarros.imageliteapi.application.jwt.JwtService;
import com.lucasbarros.imageliteapi.config.filter.JwtFilter;
import com.lucasbarros.imageliteapi.domain.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// no spring, quando vc tá dentro de uma classe de configuration e vc tá definindo um bean,
	// o q vc passar como parâmetro, se tiver dentro do contexto já registrado esses objetos,
	// eles serão injetados sem precisar de autowired, etc.
	@Bean
	public JwtFilter jwtFilter(JwtService jwtService, UserService userService) {
		return new JwtFilter(jwtService, userService);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http,
			JwtFilter jwtFilter
			) throws Exception {
		
		return http
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configure(http))
				.authorizeHttpRequests(ahr -> {
					ahr.requestMatchers("/v1/users/**").permitAll();
					ahr.requestMatchers(HttpMethod.GET, "/v1/images/**").permitAll();
					// as imagens são geradas pelo endpoint /images/uuid
					ahr.anyRequest().authenticated(); // <- genérica tem que ser a última
				})
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
		
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();

		cors.registerCorsConfiguration("/**", config); // para todas urls

		return cors;
	}

}
