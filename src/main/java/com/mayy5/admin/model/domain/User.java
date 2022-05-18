package com.mayy5.admin.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.type.UserMetaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.w3c.dom.stylesheets.LinkStyle;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_TABLE")
public class User {

	@Id
	@Column(name = "USER_ID")
	private String id;

	@Column(unique = true)
	private String email;

	@Column
	private String password;

	@Column
	private String name;

	@Column(unique = true)
	private String phone;

	@Column
	private boolean valid;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	@CollectionTable(
		name = "USER_META",
		joinColumns = @JoinColumn(name = "ID")
	)
	@MapKeyColumn(name = "META_TYPE")
	@Column(name = "META_VALUE")
	private Map<UserMetaType, String> meta = new HashMap<>();

	@Column(name = "CREATE_AT", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createAt;

	@Column(name = "UPDATE_AT")
	@UpdateTimestamp
	private LocalDateTime updateAt;

	public boolean isValid() {
		return id.equals(AuthConstant.ADMIN_USER) || valid;
	}
}
