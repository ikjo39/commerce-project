package com.ikjo39.commerce.controller;

import com.ikjo39.commerce.entity.Member;
import com.ikjo39.commerce.model.MemberResponse;
import com.ikjo39.commerce.model.SignUpForm;
import com.ikjo39.commerce.model.UpdateForm;
import com.ikjo39.commerce.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

		return ResponseEntity.ok(member);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> memberInfo(@PathVariable Long id){
		Member member = memberService.getMember(id);
		MemberResponse memberResponse = MemberResponse.of(member);

		return ResponseEntity.ok(memberResponse);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid UpdateForm form) {

		Member member = memberService.update(id, form);

		return ResponseEntity.ok(member);
	}




}
