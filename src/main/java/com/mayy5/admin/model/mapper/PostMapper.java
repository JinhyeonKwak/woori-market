package com.mayy5.admin.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.model.req.PostRequestDto;
import com.mayy5.admin.model.res.PostResponseDto;

@Component
@Mapper(componentModel = "spring", uses = UserQualifier.class)
public interface PostMapper {

	Post toEntity(PostRequestDto rto);

	@Mapping(source = "user", target = "userId", qualifiedByName = "UserToUserId")
	PostResponseDto toDto(Post post);

}

