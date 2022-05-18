package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetailerRepository extends JpaRepository<Retailer, Long> {

    @Query("select r from Retailer r where r.user.id = :userId")
    List<Retailer> getRetailersByUserId(String userId);
}
