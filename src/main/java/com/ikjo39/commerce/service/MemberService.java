package com.ikjo39.commerce.service;

import static com.ikjo39.commerce.type.ErrorCode.ALREADY_IN_ID;
import static com.ikjo39.commerce.type.ErrorCode.MEMBER_NOT_FOUND;
import static com.ikjo39.commerce.type.Role.ROLE_MEMBER;
import static com.ikjo39.commerce.type.Status.ING;

import com.ikjo39.commerce.entity.Member;
import com.ikjo39.commerce.exception.CustomException;
import com.ikjo39.commerce.model.MemberUpdateForm;
import com.ikjo39.commerce.model.SignUpForm;
import com.ikjo39.commerce.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member register(SignUpForm form) {
		boolean exists = memberRepository.existsByEmail(form.getEmail());
		if (exists) {
			throw new CustomException(ALREADY_IN_ID);
		}
		Member member = Member.builder()
			.email(form.getEmail())
			.name(form.getName())
			.password(getEncryptPassword(form.getPassword()))
			.phoneNumber(form.getPhoneNumber())
			.status(ING)
			.role(ROLE_MEMBER)
			.build();
		return memberRepository.save(member);
	}

	public Member getMember(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return member;
	}


	public Member update(Long id, MemberUpdateForm form) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		member.setPhoneNumber(form.getPhoneNumber());
		member.setZipcode(form.getZipCode());
		member.setAddress(form.getAddress());
		member.setAddressDetail(form.getAddressDetail());
		return memberRepository.save(member);
	}

	private String getEncryptPassword(String password) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder.encode(password);
	}
}
