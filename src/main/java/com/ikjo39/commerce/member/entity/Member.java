package com.ikjo39.commerce.member.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.envers.AuditOverride;

import com.ikjo39.commerce.common.entity.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "MEMBER")
@AuditOverride(forClass = BaseEntity.class)
public class Member extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String email;
	private String name;
	private String password;
	private String phoneNumber;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Enumerated(EnumType.STRING)
	private Role role;
	private String zipcode;
	private String address;
	private String addressDetail;
}
