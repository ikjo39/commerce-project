package com.ikjo39.commerce.member.dto;

import com.ikjo39.commerce.member.entity.Member;
import com.ikjo39.commerce.member.entity.Role;
import com.ikjo39.commerce.member.entity.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {
	private long id;
	private String email;
	private String name;
	private String phoneNumber;
	private Status status;
	private Role role;
	private String zipcode;
	private String address;
	private String addressDetail;

	public static MemberDto from(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.email(member.getEmail())
			.name(member.getName())
			.phoneNumber(member.getPhoneNumber())
			.status(member.getStatus())
			.role(member.getRole())
			.zipcode(member.getZipcode())
			.address(member.getAddress())
			.addressDetail(member.getAddressDetail())
			.build();
	}
}
