package com.ikjo39.commerce.member.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.model.MemberResponse;
import com.ikjo39.commerce.member.model.SignUpForm;
import com.ikjo39.commerce.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/signUp") // 로그인 처리하면서 말해줌
@RequiredArgsConstructor
public class SignUpController {
	private final MemberService memberService;

	@PostMapping("/member")
	public ResponseEntity<?> registerMember(@RequestBody @Valid SignUpForm form) {
		Member member = memberService.registerMember(form);
		return ResponseEntity.ok(MemberResponse.of(member));
	}

	@PostMapping("/admin")
	public ResponseEntity<?> registerAdmin(@RequestBody @Valid SignUpForm form) {
		Member member = memberService.registerAdmin(form);
		return ResponseEntity.ok(MemberResponse.of(member));
	}
}
