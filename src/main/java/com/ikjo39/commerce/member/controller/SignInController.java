package com.ikjo39.commerce.member.controller;

import com.ikjo39.commerce.member.model.SignInForm;
import com.ikjo39.commerce.member.service.SignInApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signIn") // 로그인 처리하면서 말해줌
@RequiredArgsConstructor
public class SignInController {

	private final SignInApplication signInApplication;

	@PostMapping("/member")
	public ResponseEntity<String> signInMember(@RequestBody SignInForm form) {
		return ResponseEntity.ok(signInApplication.memberLoginToken(form));
	}

	@PostMapping("/admin")
	public ResponseEntity<String> signInAdmin(@RequestBody SignInForm form) {
		return ResponseEntity.ok(signInApplication.adminLoginToken(form));
	}
}
