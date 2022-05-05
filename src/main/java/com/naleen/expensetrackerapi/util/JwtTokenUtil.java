package com.naleen.expensetrackerapi.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();

		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}

	public String getUserNameFromToken(String jwtToken) {
		return getClaimFromToken(jwtToken, Claims::getSubject);
	}

	public boolean validateToken(String jwtToken, UserDetails userDetails) {

		final String userName = getUserNameFromToken(jwtToken);

		return userName.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);

	}

	private boolean isTokenExpired(String jwtToken) {
		final Date expiration = getExpirationDate(jwtToken);
		return expiration.before(new Date());
	}

	private Date getExpirationDate(String jwtToken) {

		return getClaimFromToken(jwtToken, Claims::getExpiration);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {

		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		return claimResolver.apply(claims);

	}

}
