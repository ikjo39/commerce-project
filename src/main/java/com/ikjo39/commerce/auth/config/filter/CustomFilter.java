package com.ikjo39.commerce.auth.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.ikjo39.commerce.auth.common.UserVo;
import com.ikjo39.commerce.auth.config.JwtAuthenticationProvider;
import com.ikjo39.commerce.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@WebFilter(urlPatterns = "/member/*, /admin/*")
@RequiredArgsConstructor
public class CustomFilter implements Filter {
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	private final MemberService memberService;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		String token = httpServletRequest.getHeader("X-AUTH-TOKEN");
		if (!jwtAuthenticationProvider.validateToken(token)) {
			throw new ServletException("Invalid Access");
		}
		UserVo vo = jwtAuthenticationProvider.getUserVo(token);
		memberService.findByIdAndEmail(vo.getId(), vo.getEmail())
			.orElseThrow(() -> new ServletException("Invalid Access"));
		chain.doFilter(request, response);
	}
}
