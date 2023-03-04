package com.example.demo.jwtauth;

	import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
	
	private final String secret = "+MbQeThWmZq4t7w!z$C&F)J@NcRfUjXn";
	

	public String createToken(
			Map<String, Object> claims,
			UserDetails userDetails) {
				return Jwts.builder()
						.setClaims(claims)
						.setSubject(userDetails.getUsername())
						.setIssuedAt(new Date(System.currentTimeMillis()))
						.setExpiration(new Date(System.currentTimeMillis() + 1000000 * 24))
						.signWith(SignatureAlgorithm.HS256, getSignInKey(secret))
						.compact();
		
	}

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
    private boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser().setSigningKey(getSignInKey(secret)).parseClaimsJws(token).getBody().getExpiration();
    }

    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(getSignInKey(secret)).parseClaimsJws(token).getBody().getSubject();
    }
    private byte[] getSignInKey(String key) {
        String keyEncoded = Base64.getEncoder().encodeToString(key.getBytes());

        return keyEncoded.getBytes();
      }
}
