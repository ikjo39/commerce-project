package com.ikjo39.commerce.member.service;

import static com.ikjo39.commerce.common.type.ErrorCode.ALREADY_IN_ID;
import static com.ikjo39.commerce.common.type.ErrorCode.MEMBER_NOT_FOUND;
import static com.ikjo39.commerce.common.type.ErrorCode.NEW_AND_RE_PASSWORD_NOT_MATCH;
import static com.ikjo39.commerce.common.type.ErrorCode.PASSWORD_NOT_MATCH;
import static com.ikjo39.commerce.member.entity.Role.ROLE_ADMIN;
import static com.ikjo39.commerce.member.entity.Role.ROLE_MEMBER;
import static com.ikjo39.commerce.member.entity.Status.ING;

import com.ikjo39.commerce.auth.util.Aes256Util;
import com.ikjo39.commerce.common.exception.CustomException;
import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.model.MemberUpdateForm;
import com.ikjo39.commerce.member.model.MemberUpdatePassword;
import com.ikjo39.commerce.member.model.SignInForm;
import com.ikjo39.commerce.member.model.SignUpForm;
import com.ikjo39.commerce.member.repository.MemberRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member registerMember(SignUpForm form) {
		boolean exists = memberRepository.existsByEmail(form.getEmail());
		if (exists) {
			throw new CustomException(ALREADY_IN_ID);
		}
		Member member = Member.builder()
			.email(form.getEmail())
			.name(form.getName())
			.password(Aes256Util.encrypt(form.getPassword()))
			.phoneNumber(form.getPhoneNumber())
			.status(ING)
			.role(ROLE_MEMBER)
			.build();
		return memberRepository.save(member);
	}

	public Page<Member> getAllMember(Pageable pageable) {
		return memberRepository.findAll(pageable);
	}

	public Member registerAdmin(SignUpForm form) {
		boolean exists = memberRepository.existsByEmail(form.getEmail());
		if (exists) {
			throw new CustomException(ALREADY_IN_ID);
		}
		Member member = Member.builder()
			.email(form.getEmail())
			.name(form.getName())
			.password(Aes256Util.encrypt(form.getPassword()))
			.phoneNumber(form.getPhoneNumber())
			.status(ING)
			.role(ROLE_ADMIN)
			.build();
		return memberRepository.save(member);
	}

	public Member getMember(Long id) {
		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return member;
	}

	public Member getMemberByEmail(String email) {
		Member member = memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		return member;
	}

	public Member update(Long id, MemberUpdateForm form) {
		Member member = getMember(id);
		member.setPhoneNumber(form.getPhoneNumber());
		member.setZipcode(form.getZipCode());
		member.setAddress(form.getAddress());
		member.setAddressDetail(form.getAddressDetail());
		return memberRepository.save(member);
	}

	public Member updatePassword(String email, MemberUpdatePassword form) {
		Member member = getMemberByEmail(email);
		if (!Objects.equals(Aes256Util.decrypt(member.getPassword()), form.getPassword())) {
			throw new CustomException(PASSWORD_NOT_MATCH);
		}
		if (!Objects.equals(form.getNewPassword(), form.getReNewPassword())) {
			throw new CustomException(NEW_AND_RE_PASSWORD_NOT_MATCH);
		}
		member.setPassword(Aes256Util.encrypt(form.getNewPassword()));
		return memberRepository.save(member);
	}

	public Optional<Member> findValidMember(SignInForm form) {
		return memberRepository.findByEmail(form.getEmail()).stream()
			.filter(customer ->
				Objects.equals(Aes256Util.decrypt(customer.getPassword()), form.getPassword())
					&& customer.getRole() == ROLE_MEMBER)
			.findFirst();
	}

	public Optional<Member> findValidAdmin(SignInForm form) {
		return memberRepository.findByEmail(form.getEmail()).stream()
			.filter(customer ->
				Objects.equals(Aes256Util.decrypt(customer.getPassword()), form.getPassword())
					&& customer.getRole() == ROLE_ADMIN)
			.findFirst();
	}

	public Optional<Member> findByIdAndEmail(Long id, String email) {
		return memberRepository.findById(id)
			.stream().filter(customer -> customer.getEmail().equals(email))
			.findFirst();
	}
}
