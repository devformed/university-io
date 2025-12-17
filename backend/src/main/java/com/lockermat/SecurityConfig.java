package com.lockermat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public Converter<Jwt, JwtAuthenticationToken> authConverter() {
		return jwt -> new JwtAuthenticationToken(jwt, Set.of());
	}

	@Bean
	public SecurityFilterChain securityChain(HttpSecurity http, Converter<Jwt, JwtAuthenticationToken> authConverter) {
		return http.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(httpRequests -> httpRequests
						.anyRequest().authenticated()
				)
				.oauth2ResourceServer(resourceServer -> resourceServer.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(authConverter)))
				.build();
	}
}
