package com.ikjo39.commerce.member.repository;

import com.ikjo39.commerce.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByEmail(String email);

	Optional<Member> findByEmail(String username);

	@EntityGraph(attributePaths = "authorities")
	Optional<Member> findOneWithAuthoritiesByEmail(String email);
}
