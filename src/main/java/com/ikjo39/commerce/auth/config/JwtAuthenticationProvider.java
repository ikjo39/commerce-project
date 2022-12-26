package com.ikjo39.commerce.auth.config;

import com.ikjo39.commerce.auth.common.UserVo;
import com.ikjo39.commerce.auth.util.Aes256Util;
import com.ikjo39.commerce.member.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationProvider {

	private final String SECRET_KEY = "secretKey";

	private final long TOKEN_VALID_TIME = 1000L * 60 * 60 * 24;

	public String createToken(String userPk, Long id, Role role) {
		Claims claims = Jwts.claims().setSubject(Aes256Util.encrypt(userPk))
			.setId(Aes256Util.encrypt(id.toString()));
		claims.put("role", role);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
			.compact();
	}

	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET_KEY)
				.parseClaimsJws(jwtToken);
			return true;
		} catch (ExpiredJwtException e) {
			log.warn("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			log.warn("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			log.warn("JWT 토큰이 잘못되었습니다.");
		}
		return false;
	}

	public UserVo getUserVo(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		return new UserVo(Long.valueOf(Objects.requireNonNull(Aes256Util.decrypt(claims.getId()))),
			Aes256Util.decrypt(claims.getSubject()));
	}
}

