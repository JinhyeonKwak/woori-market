package com.mayy5.admin.model.mapper;

import org.mapstruct.Named;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.domain.User;

@Component
public class UserQualifier {

	private PasswordEncoder passwordEncoder;

	public UserQualifier(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Named("EncodePassword")
	public String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Named("UserToUserId")
	public String userToUserId(final User user) {
		return user.getId();
	}

}
