package com.ikjo39.commerce.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikjo39.commerce.auth.common.UserVo;
import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/member")
public class AdminMemberController {
	private final MemberService memberService;
	private final JwtAuthenticationProvider provider;

	@GetMapping
	public ResponseEntity<?> searchMember(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		final Pageable pageable) {
		UserVo vo = provider.getUserVo(token);
		Page<Member> members = memberService.getAllMember(pageable);
		return ResponseEntity.ok(members);
	}
}
