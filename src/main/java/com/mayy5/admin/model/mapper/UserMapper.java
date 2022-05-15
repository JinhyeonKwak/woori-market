package com.mayy5.admin.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.model.req.SignUpRequestDto;
import com.mayy5.admin.model.req.UserUpdateRequestDto;
import com.mayy5.admin.model.res.UserResponseDto;

@Component
@Mapper(componentModel = "spring", uses = UserQualifier.class)
public interface UserMapper {

	@Mapping(source = "password", target = "password", qualifiedByName = {"EncodePassword"})
	User toEntity(SignUpRequestDto dto);

	@Mapping(source = "password", target = "password", qualifiedByName = {"EncodePassword"})
	User toEntity(UserUpdateRequestDto dto);

	UserResponseDto toDto(User entity);
}

