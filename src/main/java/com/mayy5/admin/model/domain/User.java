package com.mayy5.admin.model.domain;

import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.type.UserMetaType;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_TABLE")
public class User extends BaseTime {

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

	public boolean isValid() {
		return id.equals(AuthConstant.ADMIN_USER) || valid;
	}
}
