package com.mayy5.admin.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.security.JWTConstant;
import com.mayy5.admin.type.UserMetaType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class TokenService {

	// 서버가 켜지면 SECRET_KEY는 매번 초기화 된다.
	// 즉 반대로 서버가 꺼졋다 켜지면 기존 Token은 무효
	final private String SECRET_KEY = UUID.randomUUID().toString();
	// User에 대한 변경사항이 있을경우 해당 코드가 변경되고 이를 기반으로 기존 유저의 수정이 발생했을때 토큰을 무효화한다.
	final private Map<String, Integer> USER_SECRET_KEY = new HashMap<>();
	final private UserService userService;

	private void setUserCode(String username, LocalDateTime updateTime) {
		USER_SECRET_KEY.put(username, updateTime.hashCode());
	}

	public int getUserCode(String username) {
		return USER_SECRET_KEY.get(username);
	}

	public String genSignUpAuthToken(String email) {
		String token = Jwts
			.builder()
			.setSubject("가입 확인")
			.setIssuer("mayy5_admin")
			.claim("email", email)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWTConstant.SIGNUP_TOKEN_EXPIRED))
			.signWith(SignatureAlgorithm.HS512,
				SECRET_KEY.getBytes()).compact();
		return token;
	}

	public String genAccessToken(String username) {
		User user = userService.getUser(username);
		setUserCode(username, user.getUpdateAt());
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
			.commaSeparatedStringToAuthorityList(user.getMeta().get(UserMetaType.ROLE));

		String token = Jwts
			.builder()
			.setSubject(username)
			.setIssuer("mayy5_admin")
			.claim("authorities",
				grantedAuthorities.stream()
					.map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList()))
			.claim("code", USER_SECRET_KEY.get(username))
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWTConstant.ACCESS_TOKEN_EXPIRED))
			.signWith(SignatureAlgorithm.HS512,
				SECRET_KEY.getBytes()).compact();
		return token;
	}

	public String genRefreshToken(String username) {
		String token = Jwts
			.builder()
			.setSubject(username)
			.setIssuer("mayy5_admin")
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWTConstant.REFRESH_TOKEN_EXPIRED))
			.signWith(SignatureAlgorithm.HS512,
				SECRET_KEY.getBytes()).compact();
		return token;
	}

	public String genAccessTokenByRefreshToken(String refreshToken) {
		Claims claims = validateToken(refreshToken);
		String username = claims.getSubject();
		return genAccessToken(username);
	}

	public Claims validateToken(String jwtToken) {
		return Jwts.parser().setSigningKey(SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
	}
}

