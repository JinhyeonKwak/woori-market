package com.mayy5.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mayy5.admin.model.domain.Retailer;

@Repository
public interface RetailerRepository extends JpaRepository<Retailer, Long> {

	@Query("select r from Retailer r where r.user.id = :userId")
	List<Retailer> getRetailersByUserId(String userId);
}
