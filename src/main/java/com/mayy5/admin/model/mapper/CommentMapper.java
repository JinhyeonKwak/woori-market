package com.mayy5.admin.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.domain.Comment;
import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.req.CommentRequestDto;
import com.mayy5.admin.model.res.CommentResponseDto;

@Component
@Mapper(componentModel = "spring", uses = UserQualifier.class)
public interface CommentMapper {

	Comment toEntity(CommentRequestDto dto);

	@Mapping(source = "user", target = "userId", qualifiedByName = "UserToUserId")
	@Mapping(source = "post", target = "postId", qualifiedByName = "PostToPostId")
	CommentResponseDto toDto(Comment entity);

	@Named("PostToPostId")
	default Long postToPostIdx(final Post post) {
		return post.getIdx();
	}
}

