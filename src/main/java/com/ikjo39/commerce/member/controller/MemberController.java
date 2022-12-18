package com.ikjo39.commerce.member.controller;

import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.model.MemberResponse;
import com.ikjo39.commerce.member.model.MemberUpdatePassword;
import com.ikjo39.commerce.member.model.SignUpForm;
import com.ikjo39.commerce.member.model.MemberUpdateForm;
import com.ikjo39.commerce.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid SignUpForm form) {
		Member member = memberService.register(form);
		return ResponseEntity.ok(MemberResponse.of(member));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> memberInfo(@PathVariable Long id) {
		Member member = memberService.getMember(id);
		return ResponseEntity.ok(member);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
		@RequestBody @Valid MemberUpdateForm form) {
		Member member = memberService.update(id, form);
		return ResponseEntity.ok(member);
	}

	@PatchMapping("/newPassword/{email}")
	public ResponseEntity<?> changePassword(@PathVariable String email, @RequestBody @Valid MemberUpdatePassword form) {
		return ResponseEntity.ok(memberService.updatePassword(email, form));
	}
}
