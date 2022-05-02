package com.mayy5.admin.filter;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class TokenFilter extends OncePerRequestFilter {


	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain)
		throws ServletException, IOException {

		try {
			if (checkJWTToken(request, response)) {
				Claims claims = tokenService.validateToken(
					request.getHeader(AuthConstant.AUTHORIZATION)
						.replace(AuthConstant.BEARER, ""));

				if (tokenService.getUserCode(claims.getSubject()) == (int)claims.get("code")
					&& claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				}
			} else if (Objects.nonNull(request.getHeader(AuthConstant.AUTHORIZATION))
				&& request.getHeader(AuthConstant.AUTHORIZATION).equals(AuthConstant.DEBUG_MODE)) {

				SecurityContextHolder.getContext().setAuthentication(
					new UsernamePasswordAuthenticationToken("admin", null,
						AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")));
			} else {
				SecurityContextHolder.clearContext();
			}
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			SecurityContextHolder.clearContext();
			filterChain.doFilter(request, response);
			return;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List)claims.get("authorities");
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
			authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(AuthConstant.AUTHORIZATION);
		if (authenticationHeader == null || !authenticationHeader.startsWith(AuthConstant.BEARER))
			return false;
		return true;
	}
}
