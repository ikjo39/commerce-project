package com.ikjo39.commerce.member.service;

import static com.ikjo39.commerce.common.type.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.entity.Role;
import com.ikjo39.commerce.member.model.SignInForm;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SignInApplication {
	private final JwtAuthenticationProvider provider;
	private final MemberService memberService;

	public String memberLoginToken(SignInForm form) {
		Member member = memberService.findValidMember(form)
			.orElseThrow(() -> new CustomException(INVALID_ID_OR_PASSWORD));
		return provider.createToken(member.getEmail(), member.getId(), Role.ROLE_MEMBER);
	}

	public String adminLoginToken(SignInForm form) {
		Member member = memberService.findValidAdmin(form)
			.orElseThrow(() -> new CustomException(INVALID_ID_OR_PASSWORD));
		return provider.createToken(member.getEmail(), member.getId(), Role.ROLE_ADMIN);
	}
}
