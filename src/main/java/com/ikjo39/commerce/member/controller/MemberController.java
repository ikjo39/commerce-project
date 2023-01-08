package com.ikjo39.commerce.member.controller;

import static com.ikjo39.commerce.common.type.ErrorCode.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ikjo39.commerce.auth.common.UserVo;
import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.member.dto.MemberDto;
import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.model.MemberUpdateForm;
import com.ikjo39.commerce.member.model.MemberUpdatePassword;
import com.ikjo39.commerce.member.service.MemberService;
import com.ikjo39.commerce.member.service.SignInApplication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {
	private final SignInApplication signInApplication;
	private final MemberService memberService;
	private final JwtAuthenticationProvider provider;

	@GetMapping("/getInfo")
	public ResponseEntity<MemberDto> getInfo(@RequestHeader(name = "X-AUTH-TOKEN") String token) {
		UserVo vo = provider.getUserVo(token);
		Member member = memberService.findByIdAndEmail(vo.getId(), vo.getEmail())
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return ResponseEntity.ok(MemberDto.from(member));
	}

	@PutMapping("/update")
	public ResponseEntity<MemberDto> update(@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody @Valid MemberUpdateForm form) {
		UserVo vo = provider.getUserVo(token);
		Member member = memberService.update(vo.getId(), form);
		return ResponseEntity.ok(MemberDto.from(member));
	}

	@PatchMapping("/newPassword")
	public ResponseEntity<MemberDto> changePassword(
		@RequestHeader(name = "X-AUTH-TOKEN") String token,
		@RequestBody @Valid MemberUpdatePassword form) {
		UserVo vo = provider.getUserVo(token);
		Member member = memberService.updatePassword(vo.getEmail(), form);
		return ResponseEntity.ok(MemberDto.from(member));
	}

}
