package com.ikjo39.commerce.service;

import com.ikjo39.commerce.entity.Member;
import com.ikjo39.commerce.repository.MemberRepository;
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
