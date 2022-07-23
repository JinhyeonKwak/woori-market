package com.mayy5.admin.model.domain;

import com.mayy5.admin.type.PostType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "POST")
public class Post extends BaseTime implements Serializable  {

	@Id
	@Column(name = "POST_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String title;

	@Column
	private String subTitle;

	@Column
	private String content;

	@Column
	@Enumerated(EnumType.STRING)
	private PostType postType;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	@OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Comment> comments = new ArrayList<>();
}