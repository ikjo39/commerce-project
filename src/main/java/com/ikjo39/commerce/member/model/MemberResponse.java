package com.ikjo39.commerce.member.model;

import com.ikjo39.commerce.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberResponse {
	private Long id;
	private String email;
	private String name;

	public static MemberResponse of(Member member) {
		return MemberResponse.builder()
			.id(member.getId())
			.email(member.getEmail())
			.name(member.getName())
			.build();
	}
}
