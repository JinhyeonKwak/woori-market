package com.mayy5.admin.model.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mayy5.admin.type.UserMetaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USER_TABLE")
public class User {

	@Id
	@Column
	private String id;

	@Column(unique = true)
	private String email;

	@Column
	private String password;

	@Column
	private String name;

	@Column(unique = true)
	private String phone;

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

}
