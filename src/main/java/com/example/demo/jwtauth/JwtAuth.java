package com.example.demo.jwtauth;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuth extends OncePerRequestFilter {

	@Autowired
	JwtService jwtService;

	@Autowired
	UserDetailsService userDetailService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader = request.getHeader("Authorization");
		String jwt;

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		try {

			// Removed "Bearer " to get jwt
			jwt = authHeader.substring(7);
			String userEmail = jwtService.extractUsername(jwt);

			// As long as the email field is not updated
			// the userDetails stays the same, but as soon as the email is changed due to a
			// PUT request
			// it is necessary to use the new token to identify the user's updated info.
			UserDetails userDetails = this.userDetailService.loadUserByUsername(userEmail);

			if (jwtService.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		} catch (NoSuchElementException noSuchElement) {
			String errorMessage = "El JWT en Authorization no concuerda con el email ingresado. Por cada modificacion al email se genera un nuevo token que se devuelve en la misma consulta por seguridad. Consultar en la base de datos.";
			int errorCode = HttpServletResponse.SC_UNAUTHORIZED;
			String errorBody = "{\"mensaje\": \"" + errorMessage + "\"}";

			response.setStatus(errorCode);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(errorBody);

			return;
		} catch (ExpiredJwtException expiredJwt) {
			String errorMessage = "JWT ha expirado.";
			int errorCode = HttpServletResponse.SC_UNAUTHORIZED;
			String errorBody = "{\"mensaje\": \"" + errorMessage + "\"}";

			response.setStatus(errorCode);
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(errorBody);

			return;
		}
		filterChain.doFilter(request, response);
	}

}
