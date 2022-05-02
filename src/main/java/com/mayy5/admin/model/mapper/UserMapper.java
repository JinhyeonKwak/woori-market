package com.mayy5.admin.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.req.UserCreateRTO;
import com.mayy5.admin.model.req.UserUpdateRTO;
import com.mayy5.admin.model.res.UserRTO;

@Component
@Mapper(componentModel = "spring", uses = UserMapperQualifier.class)
public interface UserMapper {

	@Mapping(source = "password", target = "password", qualifiedByName = {"EncodePassword"})
	User toEntity(UserCreateRTO dto);

	@Mapping(source = "password", target = "password", qualifiedByName = {"EncodePassword"})
	User toEntity(UserUpdateRTO dto);

	@Mapping(target = "password", ignore = true)
	UserRTO toDto(User entity);
}

