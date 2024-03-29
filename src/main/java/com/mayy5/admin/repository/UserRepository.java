package com.mayy5.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mayy5.admin.model.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	@Query("select m from User m where m.id <> ?1")
	List<User> findAllExceptOne(String email);

	boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	boolean existsByPhone(String phone);

	void deleteByEmail(String email);
}
