package com.mayy5.admin.model.mapper;

import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapperQualifier {

	private PasswordEncoder passwordEncoder;

	public UserMapperQualifier(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Named("EncodePassword")
	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

}
