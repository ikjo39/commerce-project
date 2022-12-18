//package com.ikjo39.commerce.service;
//
//import static com.ikjo39.commerce.type.ErrorCode.MEMBER_NOT_FOUND;
//
//import com.ikjo39.commerce.exception.CustomException;
//import com.ikjo39.commerce.member.repository.MemberRepository;
//import java.util.List;
//import java.util.stream.Collectors;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class MemberServiceDetail implements UserDetailsService {
//
//	private final MemberRepository memberRepository;
//
//	@Override
//	public UserDetails loadUserByUsername(String email) {
//		return memberRepository.findOneWithAuthoritiesByEmail(email).map(user -> createUser(user))
//			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
//	}
//
//	private User createUser(User user) {
//		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
//			.map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
//			.collect(Collectors.toList());
//		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
//	}
//}