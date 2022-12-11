package com.ikjo39.commerce.controller;

import com.ikjo39.commerce.entity.Member;
import com.ikjo39.commerce.service.AdminMemberService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/member")
public class AdminMemberController {
	private final AdminMemberService adminMemberService;

	@GetMapping
	public ResponseEntity<?> searchMember(final Pageable pageable) {
		Page<Member> members = adminMemberService.getAllMember(pageable);
		return ResponseEntity.ok(members);
	}


}
