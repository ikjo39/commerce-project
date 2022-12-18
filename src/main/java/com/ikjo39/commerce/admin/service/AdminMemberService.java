package com.ikjo39.commerce.admin.service;

import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminMemberService {

	private final MemberRepository memberRepository;

	public Page<Member> getAllMember(Pageable pageable) {
		return memberRepository.findAll(pageable);
	}
}
