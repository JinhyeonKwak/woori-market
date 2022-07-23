package com.mayy5.admin.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COMMENT")
@Entity
public class Comment extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String comment; // 댓글 내용

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "POST_ID")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user; // 작성자
}