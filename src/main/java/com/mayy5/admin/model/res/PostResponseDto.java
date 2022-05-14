package com.mayy5.admin.model.res;

import java.time.LocalDateTime;
import java.util.List;

import com.mayy5.admin.model.domain.Comment;
import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.type.PostType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseDto {

	private Long idx;
	private PostType postType;
	private String title;
	private String subTitle;
	private String content;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	private String userId;
	private List<Comment> comments;

}
