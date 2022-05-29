package com.mayy5.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mayy5.admin.model.domain.Market;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

}
