package com.mayy5.admin.model.res;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {

	private Long id;
	private String comment;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	private String userId;
	private Long postId;
}
