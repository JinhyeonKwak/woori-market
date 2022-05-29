package com.mayy5.admin.model.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.mayy5.admin.type.RetailerMetaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Retailer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RETAILER_ID")
	private Long id;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	@CollectionTable(
		name = "RETAILER_META",
		joinColumns = @JoinColumn(name = "RETAILER_ID")
	)
	@MapKeyColumn(name = "META_TYPE")
	@Column(name = "META_VALUE")
	private Map<RetailerMetaType, String> meta = new HashMap<>();

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "USER_ID")
	private User user;

	@OneToMany(mappedBy = "retailer", orphanRemoval = true)
	private List<MarketRetailer> marketRetailerList = new ArrayList<>();

	@Column(name = "CREATE_AT", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createAt;

	@Column(name = "UPDATE_AT")
	@UpdateTimestamp
	private LocalDateTime updateAt;

	//==생성 메서드==//
	public static Retailer createRetailer(User user, Map<RetailerMetaType, String> meta) {
		Retailer retailer = Retailer.builder()
			.meta(meta)
			.marketRetailerList(new ArrayList<>())
			.user(user)
			.build();
		retailer.setUser(user);
		return retailer;
	}
}
