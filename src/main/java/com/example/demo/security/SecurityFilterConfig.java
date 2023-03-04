package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.jwtauth.JwtAuthRequestFilter;

// Importing this Path because Spring Security blocks the access to h2. This method would be insecure for a production application, but works for the demo.
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console; 

@EnableWebSecurity
@Configuration
public class SecurityFilterConfig {
	
	
	@Autowired
	private JwtAuthRequestFilter jwtAuth;
	
	private AuthenticationProvider authenticationProvider;
	
	public SecurityFilterConfig(JwtAuthRequestFilter jwtAuth, AuthenticationProvider authenticationProvider) {
		super();
		this.jwtAuth = jwtAuth;
		this.authenticationProvider = authenticationProvider;
	}
	
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
		.csrf()
			.disable()
			.headers()
			.frameOptions()
			.disable()
			.and()
			.authorizeHttpRequests()
			.requestMatchers("/api/v1/auth/**").permitAll()
			.requestMatchers(toH2Console())	.permitAll()
			.requestMatchers("/h2/**").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuth, UsernamePasswordAuthenticationFilter.class);
		
			
		return http.build();
	}
}
