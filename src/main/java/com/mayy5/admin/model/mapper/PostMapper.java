package com.mayy5.admin.model.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.domain.Comment;
import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.req.PostRequestDto;
import com.mayy5.admin.model.res.CommentResponseDto;
import com.mayy5.admin.model.res.PostResponseDto;

@Component
@Mapper(componentModel = "spring", uses = UserQualifier.class)
public interface PostMapper {

	Post toEntity(PostRequestDto rto);

	@Mapping(source = "comments", target = "comments", qualifiedByName = "CommentToCommentDto")
	@Mapping(source = "user", target = "userId", qualifiedByName = "UserToUserId")
	PostResponseDto toDto(Post post);

	@Named("CommentToCommentDto")
	default List<CommentResponseDto> commentToCommentDto(final List<Comment> comments) {
		return Optional.ofNullable(comments).orElse(new ArrayList<>())
			.stream().map(comment ->
				CommentResponseDto.builder()
					.id(comment.getId())
					.comment(comment.getComment())
					.userId(comment.getUser().getId())
					.createAt(comment.getCreateAt())
					.updateAt(comment.getUpdateAt())
					.postId(comment.getPost().getIdx())
					.build()
			).collect(Collectors.toList());
	}

}

