package com.mayy5.admin.model.res;

import java.time.LocalDateTime;

import com.mayy5.admin.type.PostType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostPageResponseDto {

	private Long idx;
	private PostType postType;
	private String title;
	private String subTitle;
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	private String userId;

}
