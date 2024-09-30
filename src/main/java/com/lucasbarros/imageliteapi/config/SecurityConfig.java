package com.lucasbarros.imageliteapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// csrf é quando trabalhamos com páginas web no spring, mas estamos utilizando react
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(AbstractHttpConfigurer::disable)
				// ou .csrf((csrf) -> csrf.disable())
				.cors((cors) -> cors.configure(http))
				// ou .csrf((cors) -> cors.disable())
				.authorizeHttpRequests((authorizeHttpRequests) ->
	 				authorizeHttpRequests
						//.requestMatchers("/**").hasRole("USER")
	 				.anyRequest().permitAll()
				)
				.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		// applyPermitDefaultValues() permite tudo
		CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
		UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();
		
		cors.registerCorsConfiguration("/**", config); // para todas urls
		// ...("/v1/users", config); // ou selecione em quais vai aplicar a configuração
		
		return cors;
	}

}
