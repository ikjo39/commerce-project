package com.ikjo39.commerce.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider();
	}
}
