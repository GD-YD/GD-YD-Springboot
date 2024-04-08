package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
