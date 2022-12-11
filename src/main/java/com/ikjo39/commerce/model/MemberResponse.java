package com.ikjo39.commerce.model;


import com.ikjo39.commerce.entity.Member;
import com.ikjo39.commerce.type.Role;
import com.ikjo39.commerce.type.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberResponse {
	private long id;
	private String email;
	private String name;
	private String phoneNumber;
	private Status status;
	private Role role;
	private String zipcode;
	private String address;
	private String addressDetail;



	public static MemberResponse of(Member member) {
		return MemberResponse.builder()
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
