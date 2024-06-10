package com.mfc.auth.auth.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mfc.auth.auth.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
	@Query("select m from Member m where m.status = 1 and m.uuid = :uuid")
	Optional<Member> findByUuid(@Param("uuid") String uuid);

	@Query("Select m from Member m where m.status = 1 and  m.phone = :phone")
	Optional<Member> findByActivePhone(@Param("phone") String phone);

	Optional<Member> findByPhone(String phone);
}
